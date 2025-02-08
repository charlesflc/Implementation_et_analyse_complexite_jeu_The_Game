import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Export_Graphiques{
private Export_Graphiques() {

}


    public static void export(String pathFile, String PlotName) {
    

        try {
            ProcessBuilder pb = new ProcessBuilder("venv/bin/python3",  "Plot.py",pathFile, "Data/"+PlotName);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            System.out.println("Execution de la commande : " + "venv/bin/python3 Plot.py " + pathFile + " Data/"+PlotName);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = p.waitFor();
            if(exitCode != 0){
                System.out.println("Erreur lors de la création du graphique. Code de sortie : " + exitCode);
            }else{
                System.out.println("Graphique créé avec succès. Code de sortie : " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
    
       Export_Graphiques.export("Data/Minimiser_Saut10_3J_10M.csv", "Minimiser_Saut10_3J_10M");
    }

}
