package ca.bcit.comp2522.termproject.defendtheanimals;

import ca.bcit.comp2522.termproject.defendtheanimals.scenes.GeneralScene;
import ca.bcit.comp2522.termproject.defendtheanimals.sprites.BasicDemon;
import ca.bcit.comp2522.termproject.defendtheanimals.sprites.SpeedDemon;
import ca.bcit.comp2522.termproject.defendtheanimals.sprites.TankDemon;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.util.*;

/**
 * Represents a level in the game.
 * @author Al & Bosco
 * @version 2022
 */
public class Level {
    private final int level;
    private int numOfWaves;

    private HashMap<Integer, Stack<String>> waves;
    private Random random = new Random();

    /**
     * Constructs a level.
     * @param level An integer indicating the level of the game.
     */
    public Level(final int level) {
        this.level = level;
        waves = new HashMap<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/levels.json"))));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject levelConfig = (JSONObject) jsonObject.get(Integer.toString(level));
            JSONArray wavesData = (JSONArray) levelConfig.get("waves");

            numOfWaves = wavesData.size();
            Iterator<JSONObject> iterator = wavesData.iterator();
            int waveNum = 0;
            while (iterator.hasNext()) {
                JSONObject waveData = iterator.next();
                waveNum += 1;
                Stack<String> wave = new Stack<>();

                for (Iterator i = waveData.keySet().iterator(); i.hasNext ();) {
                    String demonType = (String) i.next();
                    long demonNum = (long) waveData.get(demonType);
                    for (long j = 0; j < demonNum; j++) {
                        wave.push(demonType);
                    }
                }
                Collections.shuffle(wave);
                waves.put(waveNum, wave);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the level as an integer.
     * @return An integer indicating the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the number of waves in the level.
     * @return An integer indicating the number of waves in this level.
     */
    public int getNumOfWaves() {
        return numOfWaves;
    }

    /**
     * Returns a list of demons at the start of the wave.
     * @param currentWaveNum the current wave.
     * @return a list of demons at the start of the current wave.
     */
    public ArrayList<BasicDemon> getInitialDemonsFromWave(final int currentWaveNum) {
        int numberOfInitialDemons = 2 + level;

        ArrayList<BasicDemon> initialDemonsList = new ArrayList<>();

        for (int i = 0; i < numberOfInitialDemons; i++) {
            initialDemonsList.add(getDemonFromWave(currentWaveNum));
        }

        return initialDemonsList;
    }

    /**
     * Checks if there is any demons left in the current wave.
     * @param currentWaveNum The wave number.
     * @return true if there is demons left in the current wave, otherwise, false.
     */
    public boolean hasDemonsLeftInWave(int currentWaveNum) {
        List<String> wave = waves.get(currentWaveNum);
        return wave.size() > 0;
    }

    /**
     * Gets a demon from the current wave.
     * @param currentWaveNum The wave number.
     * @return A demon from the current wave.
     */
    public BasicDemon getDemonFromWave(int currentWaveNum) {
        Stack<String> wave = waves.get(currentWaveNum);

        if (hasDemonsLeftInWave(currentWaveNum)) {

            String demonType = wave.pop();

            BasicDemon newDemon = createDemon(demonType);

            return newDemon;

        }
        return null;
    }



    public BasicDemon createDemon(final String demonType) {
        double x = GeneralScene.GAME_WIDTH;
        double y = random.nextInt(5) * 80 + 220;

        BasicDemon demon = null;
        switch (demonType) {
            case "BasicDemon":
                demon = new BasicDemon("images/" + demonType + "Sprites", BasicDemon.BASIC_DEMON_WIDTH, BasicDemon.BASIC_DEMON_HEIGHT);
                demon.setPosition(x,y);
                break;
            case "TankDemon":
                demon = new TankDemon("images/" + demonType + "Sprites");
                demon.setPosition(x,y);
                break;
            case "SpeedDemon":
                demon = new SpeedDemon("images/" + demonType + "Sprites");
                demon.setPosition(x,y);
        }


        return demon;
    }
}

