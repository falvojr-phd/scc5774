package bot;

// Copyright 2015 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//  
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

import java.util.Scanner;

/**
 * MyBot class
 * 
 * Main class that will keep reading output from the engine.
 * Will either update the bot state or get actions.
 * 
 * @author Venilton FalvoJr <falvojr@gmail.com>, Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */
public class BotParser {

    private static int botId = 0;
    
	private final Scanner scan;
	private final BotStarter bot;
    private Field field;

    
    public BotParser(BotStarter bot) {
		this.scan = new Scanner(System.in);
		this.bot = bot;
	}
    
    public void run() {
        field = new Field(0, 0);
        while(scan.hasNextLine()) {
        	final String line = scan.nextLine();

            if(line.length() == 0) {
                continue;
            }

            final String[] parts = line.split(" ");
            
            if(parts[0].equals("settings")) {
                if (parts[1].equals("field_columns")) {
                    field.setCols(Integer.parseInt(parts[2]));
                }
                if (parts[1].equals("field_rows")) {
                    field.setRows(Integer.parseInt(parts[2]));
                }
                if (parts[1].equals("your_botid")) {
                	BotParser.setBotId(Integer.parseInt(parts[2]));
                    field.setPlayer(BotParser.getBotId());
                }
            } else if(parts[0].equals("update")) {
                if (parts[2].equals("field")) {
                	final String data = parts[3];
                    field.parseFromString(data);
                }
            } else if(parts[0].equals("action")) {
                if (parts[1].equals("move")) {
                	final int column = bot.makeTurn(field);
                    System.out.println("place_disc " + column);
                }
            }
            else { 
                System.out.println("unknown command");
            }
        }
    }
    
	public static int getBotId() {
		return BotParser.botId;
	}

	public static void setBotId(int botId) {
		BotParser.botId = botId;
	}

	public static int getEnemyId() {
		return BotParser.botId == 1 ? 2 : 1;
	}
}