
package levenshtein;

import interfaz.LevenshteinInterfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;


public class MainController implements ActionListener{
    
    LevenshteinInterfaz interfaz;
    
    MainController(){
        interfaz = new LevenshteinInterfaz();
        interfaz.asignarEscuchas(this);
    }
    
    public String minimo(String eliminar, String agregar, String sustituir) {
        
        String[] elimina = eliminar.split("-");//numero & direccion
        String[] agrega = agregar.split("-");//numero & direccion
        String[] sustituye = sustituir.split("-");//numero & direccion
        
        int a = Integer.parseInt(elimina[0]);
        int b = Integer.parseInt(agrega[0]);
        int c = Integer.parseInt(sustituye[0]);
        
        String dato = "";
        
        
        if (a < b && a < c) {//elimina
            a += 1;
            dato = a+"-IZQ";
        }else{
            
            if (b < a && b < c) {//anade

                b += 1;
                dato = b + "-SUP";
                
            } else {//anade

                c += 1;
                dato = c + "-DIA";
            }
        
        }
        
        return dato;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == interfaz.getbCalcular()){
            
            if(interfaz.validarCampops()){
                
                if(interfaz.getTipo_solucion().getSelectedIndex() == 1){                    
            
                    String cadenaV = interfaz.getCadenaV().getText();
                    String cadenaU = interfaz.getCadenaU().getText();

                    int longitudV = cadenaV.length();
                    int longitudU = cadenaU.length();


                    String [][]Levenshtein = new String [longitudV+1][longitudU+1];


                    //Levenshtein[0][0] = "0-FIN";//seteamos el final e inicio de la matriz

                    for(int i = 0; i<=longitudV; i++){
                        Levenshtein[i][0] = i + "-FIN";
                    }

                    for(int i = 0; i<=longitudU; i++){
                        Levenshtein[0][i] = i + "-FIN";
                    }
                    
                    Levenshtein[0][0] = "0-FIN";//seteamos el final e inicio de la matriz

                    for(int i = 1; i<=longitudV; i++){
                        for(int j = 1; j<=longitudU; j++){
                            
                            if(cadenaV.charAt(i-1) == cadenaU.charAt(j-1)){
                                
                                //System.out.print(Levenshtein[i-1][j-1] + "ddd" + cadenaV.charAt(i-1) + cadenaV.charAt(j-1));
                                String[] sustituye = Levenshtein[i-1][j-1].split("-");//numero & direccion
                                
                                Levenshtein[i][j] = sustituye[0] + "-DIA";

                            }else{
                                Levenshtein[i][j] = minimo(Levenshtein[i][j-1],Levenshtein[i-1][j],Levenshtein[i-1][j-1]);
                            }
                        }
                    }
                    
                    String resultado = "";
                    
                    resultado += "La distancia es: " + Levenshtein[longitudV][longitudU].split("-")[0] + "\n\n";
                    resultado += "************************************************************************\n\n";
                                     
                    
                    for(int i = 0; i<=longitudV; i++){
                        
                        //System.out.print("|");
                        
                        resultado += "|";
                        
                        for(int j = 0; j<=longitudU; j++){
                            
                            //System.out.print(Levenshtein[i][j]);
                            resultado += Levenshtein[i][j];
                            resultado += "    ";
                            //System.out.print("\t");
                            
                        }
                        
                        //System.out.println("|");
                        resultado += "|";
                        resultado += "\n";
                        
                        interfaz.getResultado().setText(resultado);
                        
                    }
                }
                
                    
            }
            
        }
        
    }
}
