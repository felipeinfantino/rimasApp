package rimas.rimas;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Data {
    Set<String> allWords = new HashSet<>();
    private List<List<String>> rimasConsonantes =  new ArrayList<>();
    private List<List<String>> rimasAsonantes =  new ArrayList<>();
    ArrayList<String> terminacionesConsonantes = new ArrayList<>();
    ArrayList<String> terminacionesAsonantes = new ArrayList<>();

    private String getVocalas(String palabra){
        String vocales = "";
        for(int i = 0; i < palabra.length(); i ++){
            char c = palabra.charAt(i);
            if(isVocal(c)){
                vocales += c;
            }
        }
        return vocales;
    }

    private boolean isVocal(char c){
        return c == 'a' || c == 'e' || c == 'i' || c== 'o' || c == 'u';
    }

    //Mas adelante esta funcion con las que la persona escriba
    public void addTerminacionConsonante(String terminacionConsonante) {
        if(terminacionConsonante.length() < 1) return;
        addTerminacionesConsonantes(terminacionConsonante);
        List<String> terminacionToAdd = new ArrayList<>();
        for(String word : allWords){
            boolean terminaAsi = true;
            int lange = Math.min(terminacionConsonante.length(), word.length());
            for(int i = 0 ; i <lange; i++){
                char letterFromWord = word.charAt(word.length()- 1 - i);
                char letterFromTermination = terminacionConsonante.charAt(terminacionConsonante.length()- 1 - i);
                if(letterFromWord != letterFromTermination){
                    terminaAsi = false;
                    break;
                }
            }
            if(terminaAsi){
                terminacionToAdd.add(word);
            }
        }
        addRimasConsonante(terminacionToAdd);
        Log.d("MAIN", "Terminacion agregada");
    }

    public void addTerminacionAsonante(String palabra) {
        List<String> terminacionToAdd = new ArrayList<>();
        String vocales = getVocalas(palabra);
        if(vocales.length() < 1) return;
        addTermiancionesAsonantes(palabra);
        for(String word : allWords){
            String vocalesTemp = getVocalas(word);
            if(vocalesTemp.length() <1)continue;
            int lange = Math.min(vocales.length(), vocalesTemp.length());
            lange = Math.min(3, lange);
            boolean isAsonante = true;
            for(int i = 0; i < lange; i++){
                char letterFromWord = vocalesTemp.charAt(vocalesTemp.length()- 1 - i);
                char letterFromTermination = vocales.charAt(vocales.length()- 1 - i);
                if(letterFromWord != letterFromTermination){
                    isAsonante = false;
                    break;
                }
            }
            if(isAsonante){
                terminacionToAdd.add(word);
            }
        }
        addRimasAsonantes(terminacionToAdd);
    }

    public void addWordToAllWords(String word){
        if(!allWords.contains(word)){
            allWords.add(word);
        }
    }
    private void addRimasConsonante(List<String> rimas){
        if(!rimasConsonantes.contains(rimas)){
            rimasConsonantes.add(rimas);
        }
    }

    public boolean containsConsonantTermination(String toCheck){
        return terminacionesConsonantes.contains(toCheck);
    }

    public boolean containsAsonanteTermination(String toCkhecl){
        return terminacionesAsonantes.contains(toCkhecl);
    }

    private void addRimasAsonantes(List<String> rimasAs){
        if(!rimasAsonantes.contains(rimasAs)){
            rimasAsonantes.add(rimasAs);
        }
    }

    private void addTerminacionesConsonantes(String terminacionCons){
        if(!terminacionesConsonantes.contains(terminacionCons)){
            terminacionesConsonantes.add(terminacionCons);
        }
    }

    private void addTermiancionesAsonantes(String terminacionAs){
        if(!terminacionesAsonantes.contains(terminacionAs)){
            terminacionesAsonantes.add(terminacionAs);
        }
    }

    //Para el trainer
    public List<List<String>> getRimasConsonantes(){
        return rimasConsonantes;
    }

    public List<List<String>> getRimasAsonantes() {
        return rimasAsonantes;
    }

    public ArrayList<String> getTerminacionesConsonantes() {
        return terminacionesConsonantes;
    }

    public ArrayList<String> getTerminacionesAsonantes() {
        return terminacionesAsonantes;
    }

    public String[] getRandomTerminacionesConsonante(){
        int rnd = new Random().nextInt(rimasConsonantes.size());
        List<String> randomTerminacion = rimasConsonantes.get(rnd);
        while (randomTerminacion.size() < 4){
            rnd = new Random().nextInt(rimasConsonantes.size());
            randomTerminacion = rimasConsonantes.get(rnd);
        }
        String[] rimas = new String[4];

        int rnd1 = new Random().nextInt(randomTerminacion.size());
        int rnd2 = new Random().nextInt(randomTerminacion.size());
        int rnd3 = new Random().nextInt(randomTerminacion.size());
        int rnd4 = new Random().nextInt(randomTerminacion.size());

        rimas[0] = randomTerminacion.get(rnd1);
        rimas[1] = randomTerminacion.get(rnd2);
        rimas[2] = randomTerminacion.get(rnd3);
        rimas[3] = randomTerminacion.get(rnd4);

        return rimas;

    }
    public String[] getRandomTerminacionesAsonante(){
        int rnd = new Random().nextInt(rimasAsonantes.size());
        List<String> randomTerminacion = rimasAsonantes.get(rnd);
        while (randomTerminacion.size() < 4){
            rnd = new Random().nextInt(rimasAsonantes.size());
            randomTerminacion = rimasAsonantes.get(rnd);
        }
        String[] rimas = new String[4];

        int rnd1 = new Random().nextInt(randomTerminacion.size());
        int rnd2 = new Random().nextInt(randomTerminacion.size());
        int rnd3 = new Random().nextInt(randomTerminacion.size());
        int rnd4 = new Random().nextInt(randomTerminacion.size());

        rimas[0] = randomTerminacion.get(rnd1);
        rimas[1] = randomTerminacion.get(rnd2);
        rimas[2] = randomTerminacion.get(rnd3);
        rimas[3] = randomTerminacion.get(rnd4);

        return rimas;

    }

    public void removeTerminacionConsonante(String terminacion){
        if(terminacionesConsonantes.contains(terminacion)){
            terminacionesConsonantes.remove(terminacion);
        }
    }

    public void removeTerminacionAsonante(String terminacion){
        if(terminacionesAsonantes.contains(terminacion)){
            terminacionesAsonantes.remove(terminacion);
        }
    }

}
