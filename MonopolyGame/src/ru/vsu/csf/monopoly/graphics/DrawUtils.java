package ru.vsu.csf.monopoly.graphics;

import ru.vsu.csf.monopoly.cells.Company;
import ru.vsu.csf.monopoly.objects.Player;

import java.awt.*;
import java.util.List;

public class DrawUtils {

    public static void drawButton(Graphics2D g, int x, int y, int sizeX, int sizeY, Color color, String text){
        g.setColor(color);
        g.fillOval(x-sizeX/2, y-sizeY/2, sizeY, sizeY);
        g.fillOval(x+sizeX/2-sizeY/2, y-sizeY/2, sizeY, sizeY);
        g.setColor(Color.BLACK);
        g.drawOval(x-sizeX/2, y-sizeY/2, sizeY, sizeY);
        g.drawOval(x+sizeX/2-sizeY/2, y-sizeY/2, sizeY, sizeY);
        g.setColor(color);
        g.fillRect(x-sizeX/2+sizeY/2, y-sizeY/2, sizeX-sizeY/2, sizeY);
        g.setColor(Color.BLACK);
        drawCenteredString(g, text, new Rectangle(x-sizeX/2, y-sizeY/2, sizeX, sizeY), new Font("TimesRoman", Font.PLAIN, sizeX/10));

    }

    public static void drawPlayer(Graphics2D g, int x, int y, int size, Color color, int playerNumber, int cash){
        g.setColor(color);
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
        drawCenteredString(g, "Игрок " + playerNumber, new Rectangle(x, y, size, size/2), new Font("TimesRoman", Font.PLAIN, size/8));
        drawCenteredString(g, "Ваш бюджет: " + cash, new Rectangle(x, y + size/2, size, size/2), new Font("TimesRoman", Font.PLAIN, size/10));
    }

    public static void drawActivePlayer(Graphics2D g, int x, int y, int size, Color color, int playerNumber, int cash){
        g.setColor(color);
        g.fillRect(x, y, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(x,y, size, size);
        drawCenteredString(g, "Игрок " + playerNumber, new Rectangle(x, y, size, size/2), new Font("TimesRoman", Font.PLAIN, size/8));
        drawCenteredString(g, "Ваш бюджет: " + cash, new Rectangle(x, y + size/2, size, size/2), new Font("TimesRoman", Font.PLAIN, size/10));
    }

    public static void drawPlayers(Graphics2D g, int x, int y, int sizeX, int sizeY, List<Player> players){
        if(players.size() == 1){
            g.setColor(players.get(0).getColor());
            int size = sizeX/4;
            g.fillOval(x-size/2, y-size/2, size, size);
            g.setColor(Color.BLACK);
            g.drawOval(x-size/2, y-size/2, size, size);
        }
        if(players.size() == 2){
            g.setColor(players.get(0).getColor());
            g.fillOval(x-sizeX*3/8, y-sizeY/8, sizeX/4, sizeX/4);
            g.setColor(players.get(1).getColor());
            g.fillOval(x+sizeX/8, y-sizeY/8, sizeX/4, sizeX/4);
            g.setColor(Color.BLACK);
            g.drawOval(x-sizeX*3/8, y-sizeY/8, sizeX/4, sizeX/4);
            g.drawOval(x+sizeX/8, y-sizeY/8, sizeX/4, sizeX/4);
        }
        if(players.size() == 3){
            g.setColor(players.get(0).getColor());
            g.fillOval(x-sizeX/10, y-sizeY/10, sizeX/5, sizeX/5);
            g.setColor(players.get(1).getColor());
            g.fillOval(x-sizeX/4-sizeX/5, y+sizeY/6, sizeX/5, sizeX/5);
            g.setColor(players.get(2).getColor());
            g.fillOval(x+sizeX/4, y-sizeY/6-sizeY/5, sizeX/5, sizeX/5);
            g.setColor(Color.BLACK);
            g.drawOval(x-sizeX/10, y-sizeY/10, sizeX/5, sizeX/5);
            g.drawOval(x-sizeX/4-sizeX/5, y+sizeY/6, sizeX/5, sizeX/5);
            g.drawOval(x+sizeX/4, y-sizeY/6-sizeY/5, sizeX/5, sizeX/5);
        }
        if(players.size() == 4){
            g.setColor(players.get(0).getColor());
            g.fillOval(x-sizeX*3/8, y-sizeY/2, sizeX/4, sizeX/4);
            g.setColor(players.get(1).getColor());
            g.fillOval(x+sizeX/8, y-sizeY/2, sizeX/4, sizeX/4);
            g.setColor(players.get(2).getColor());
            g.fillOval(x-sizeX*3/8, y, sizeX/4, sizeX/4);
            g.setColor(players.get(3).getColor());
            g.fillOval(x+sizeX/8, y, sizeX/4, sizeX/4);
            g.setColor(Color.BLACK);
            g.drawOval(x-sizeX*3/8, y-sizeY/2, sizeX/4, sizeX/4);
            g.drawOval(x+sizeX/8, y-sizeY/2, sizeX/4, sizeX/4);
            g.drawOval(x-sizeX*3/8, y, sizeX/4, sizeX/4);
            g.drawOval(x+sizeX/8, y, sizeX/4, sizeX/4);
        }
    }

    public static void drawCell(Graphics2D g, int x, int y, int sizeX, int sizeY, Color color, String inscription){
        g.setColor(color);
        g.fillRect(x-sizeX/2, y-sizeY/2, sizeX, sizeY);
        g.setColor(Color.BLACK);
        g.drawRect(x-sizeX/2, y-sizeY/2, sizeX, sizeY);
        drawCenteredString(g, inscription, new Rectangle(x-sizeX/2, y-sizeY/2, sizeX, sizeY), new Font("TimesRoman", Font.PLAIN, sizeX/8));
    }

    public static void drawCell(Graphics2D g, int x, int y, int sizeX, int sizeY, Color color, String inscription, int price, Company.CompanyType type, int countOfBuildings){
        g.setColor(color);
        g.fillRect(x-sizeX/2, y-sizeY/2, sizeX, sizeY*3/4);
        g.setColor(Color.BLACK);
        g.drawRect(x-sizeX/2, y-sizeY/2, sizeX, sizeY*3/4);
        drawCenteredString(g, inscription, new Rectangle(x-sizeX/2, y-sizeY/2, sizeX, sizeY*3/4), new Font("TimesRoman", Font.PLAIN, sizeX/8));
        if(type == Company.CompanyType.SPORT_WEAR){
            g.setColor(new Color(36, 245, 245));
        }
        if(type == Company.CompanyType.WEAR){
            g.setColor(new Color(248, 72, 50));
        }
        if(type == Company.CompanyType.SOCIAL_NETWORK){
            g.setColor(new Color(231, 132, 253));
        }
        if(type == Company.CompanyType.AUTO){
            g.setColor(new Color(255, 0, 0));
        }
        if(type == Company.CompanyType.GAME){
            g.setColor(new Color(241, 189, 57));
        }
        if(type == Company.CompanyType.DRINKS){
            g.setColor(new Color(124, 229, 36));
        }
        if(type == Company.CompanyType.AIRLINES){
            g.setColor(new Color(198, 2, 246));
        }
        if(type == Company.CompanyType.FAST_FOOD){
            g.setColor(new Color(11, 104, 204));
        }
        if(type == Company.CompanyType.HOTEL){
            g.setColor(new Color(162, 187, 98));
        }
        if(type == Company.CompanyType.TECHNO){
            g.setColor(new Color(157, 153, 153));
        }
        g.fillRect(x-sizeX/2, y+sizeY/4, sizeX, sizeY/4);
        g.setColor(Color.BLACK);
        g.drawRect(x-sizeX/2, y+sizeY/4, sizeX, sizeY/4);
        drawCenteredString(g, "$" + price, new Rectangle(x-sizeX/2, y+sizeY/4, sizeX, sizeY/4), new Font("TimesRoman", Font.PLAIN, sizeX/8));
        drawCenteredString(g, "(" + countOfBuildings + ")", new Rectangle(x, y-sizeY/2, sizeX/2, sizeY/3), new Font("TimesRoman", Font.PLAIN, sizeX/8));
    }

    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public static void drawStr(Graphics2D g, String text){
        g.setColor(Color.WHITE);
        drawCenteredString(g, text, new Rectangle(320, 72, 1020, 595), new Font("TimesRoman", Font.PLAIN, 30));
    }

    public static void drawDice(Graphics2D g, int x, int y, int value, int size){
        g.setColor(Color.WHITE);
        g.fillRect(x-size/2, y-size/2, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(x-size/2, y-size/2, size, size);
        if(value == 1){
            g.fillOval(x-size/10, y-size/10, size/5, size/5);
        } else if(value == 2){
            g.fillOval(x-size*3/8, y-size/8, size/4, size/4);
            g.fillOval(x+size/8, y-size/8, size/4, size/4);
        } else if(value == 3){
            g.fillOval(x-size/10, y-size/10, size/5, size/5);
            g.fillOval(x-size/4-size/5, y+size/4, size/5, size/5);
            g.fillOval(x+size/4, y-size/4-size/5, size/5, size/5);
        } else if(value == 4){
            g.fillOval(x-size*3/8, y-size/8-size/4, size/4, size/4);
            g.fillOval(x+size/8, y-size/8-size/4, size/4, size/4);
            g.fillOval(x-size*3/8, y+size/8, size/4, size/4);
            g.fillOval(x+size/8, y+size/8, size/4, size/4);
        } else if(value == 5){
            g.fillOval(x-size/8, y-size/8, size/4, size/4);
            g.fillOval(x-size*3/8, y-size/8-size/4, size/4, size/4);
            g.fillOval(x+size/8, y-size/8-size/4, size/4, size/4);
            g.fillOval(x-size*3/8, y+size/8, size/4, size/4);
            g.fillOval(x+size/8, y+size/8, size/4, size/4);
        } else if(value == 6){
            g.fillOval(x-size*3/8, y-size/2+size/16, size/4, size/4);
            g.fillOval(x+size/8, y-size/2 + size/16, size/4, size/4);
            g.fillOval(x-size*3/8, y+size/4 - size/16, size/4, size/4);
            g.fillOval(x+size/8, y+size/4 - size/16, size/4, size/4);
            g.fillOval(x-size*3/8, y-size/8, size/4, size/4);
            g.fillOval(x+size/8, y-size/8, size/4, size/4);
        }
    }
}
