import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkDOT;

import java.io.*;
import java.util.Scanner;

public class Output {

    //String fileName = "src/test2.dot";
	//String fileName = Main.fileName;
    public void createOutput(Schedule solution, String outputName){

    	while (solution.getTask() != -1){
    		
    		int task = solution.getTask();
    		int processor = solution.getProcessor();
    		int time = solution.getTime();
    		Graph graph = Input.getInputG();
    		
    		formatAttributes(graph, task, processor, time);
    		solution = solution.getParent();
    	}
    	
        //Get created graph from input dot file
        Graph graph = Input.getInputG();

        //Create the dot file writer for output. Constructor parameter "true" indicates that graph is directed
        FileSinkDOT writer = new FileSinkDOT(true);

        try{
            writer.writeAll(graph, outputName);

            //Store entire file into the string
            String fileContent = new Scanner(new File(outputName)).useDelimiter("\\Z").next();

            //Remove double quotes
            String temp = fileContent.replaceAll("\"","");
            temp = temp.replaceFirst("digraph ", "digraph \"" + outputName + "\" ");

            //Overwrite old file with new contents
            //FileWriter  writer2 = new FileWriter (new File("src/output.dot"));
            FileWriter  writer2 = new FileWriter (new File(outputName));
            writer2.write(temp);

            writer2.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void formatAttributes(Graph graph, int task, int processor, int time){
    	graph.getNode(task).addAttribute("Processor", processor + 1);
		graph.getNode(task).addAttribute("Start", time);
		graph.getNode(task).removeAttribute("ui.style");
		graph.getNode(task).removeAttribute("ui.label");
    }
}
