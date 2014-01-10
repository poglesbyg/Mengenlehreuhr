/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mengenlehreuhr;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author poglesbyg
 */
public class Mengenlehreuhr extends JApplet {

    final static int maxCharHeight = 15;
    final static int minFontSize = 6;

    final static Color bg = Color.white;
    final static Color fg = Color.black;
    final static Color red = Color.red;
    final static Color white = Color.white;
    final static Color yellow = Color.yellow;

    final static BasicStroke stroke = new BasicStroke(2.0f);
    final static BasicStroke wideStroke = new BasicStroke(8.0f);

    final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            10.0f, dash1, 0.0f);
    Dimension totalSize;
    FontMetrics fontMetrics;

    public void init() {
        //Initialize drawing colors
        setBackground(bg);
        setForeground(fg);
    }

    FontMetrics pickFont(Graphics2D g2,
            String longString,
            int xSpace) {
        boolean fontFits = false;
        Font font = g2.getFont();
        FontMetrics fontMetrics = g2.getFontMetrics();
        int size = font.getSize();
        String name = font.getName();
        int style = font.getStyle();

        while (!fontFits) {
            if ((fontMetrics.getHeight() <= maxCharHeight)
                    && (fontMetrics.stringWidth(longString) <= xSpace)) {
                fontFits = true;
            } else {
                if (size <= minFontSize) {
                    fontFits = true;
                } else {
                    g2.setFont(font = new Font(name,
                            style,
                            --size));
                    fontMetrics = g2.getFontMetrics();
                }
            }
        }

        return fontMetrics;
    }
    
//    public static void redRect() {
//        
//    }

    public void paint(Graphics g) {
        Scanner sc = new Scanner(System.in);
        String [] input = sc.nextLine().split(":");
        int[] number = new int[3];
        for (int i = 0; i < input.length; i++) {
            number[i] = Integer.parseInt(input[i]);
        }
        
        time(number[0],number[1],number[2],g);
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mengenlehreuhr m = new Mengenlehreuhr();
//        m.time(0, 0, 0);
//        m.time(13, 1, 1);
//        m.time(24, 0, 0);
        
        String input = JOptionPane.showInputDialog("Enter n:");
        int n = Integer.parseInt(input);
        
        JFrame f = new JFrame("Mengenlehreuhr");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JApplet applet = new Mengenlehreuhr();
        f.getContentPane().add("Center", applet);
        f.repaint();
        applet.init();
        f.pack();
        f.setSize(new Dimension(800, 800));
        f.setVisible(true);
    }

    public void time(int hour, int minute, int second, Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Dimension d = getSize();
        int gridWidth = d.width / 11;
        int gridHeight = d.height / 8;

        fontMetrics = pickFont(g2, "Filled and Stroked GeneralPath",
                gridWidth);

        Color fg3D = Color.lightGray;

        g2.setPaint(fg3D);
        g2.draw3DRect(0, 0, d.width - 1, d.height - 1, true);
        g2.draw3DRect(3, 3, d.width - 7, d.height - 7, false);
        g2.setPaint(fg);

        int x = 5;
        int y = 7;
        int rectWidth = gridWidth - 2 * x;
        int stringY = gridHeight - 3 - fontMetrics.getDescent();
        int rectHeight = stringY - fontMetrics.getMaxAscent() - y - 2;

        //seconds on off every other second
        if (second % 2 == 0) {
            System.out.print("Y");

            // fill Rectangle2D.Double (red)
            g2.setPaint(yellow);
            g2.fill(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
            g2.setPaint(fg);
            //g2.drawString("Filled Rectangle2D", x, stringY);
            x += gridWidth;

        } else {
            System.out.print("O");

            // draw Rectangle2D.Double
            g2.setStroke(stroke);
            g2.draw(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
            //g2.drawString("Rectangle2D", x, stringY);
            x += gridWidth;
        }
        System.out.println();
        // NEW ROW
        x = 5;
        y += gridHeight;
        stringY += gridHeight;

        //row of 4 to represent first 20 hrs in day
        int lightsOnRow = 4;
        int hourNow = hour / 5;
        for (int i = 0; i < lightsOnRow; i++) {

            if (hourNow-- > 0) {
                System.out.print("R");

                // fill Rectangle2D.Double (red)
                g2.setPaint(red);
                g2.fill(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
                g2.setPaint(fg);
                //g2.drawString("Filled Rectangle2D", x, stringY);
                x += gridWidth;

            } else {
                System.out.print("O");

                // draw Rectangle2D.Double
                g2.setStroke(stroke);
                g2.draw(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
                //g2.drawString("Rectangle2D", x, stringY);
                x += gridWidth;
            }

        }
        System.out.println();
        // NEW ROW
        x = 5;
        y += gridHeight;
        stringY += gridHeight;

        //row of 4 to represent any non % 5 hour. ex. 1,2,3,4
        int hourInDay = hour % 5;
        int lightsOnSecondRow = 4;
        for (int i = 0; i < lightsOnSecondRow; i++) {
            if (hourInDay > 0) {
                System.out.print("R");

                // fill Rectangle2D.Double (red)
                g2.setPaint(red);
                g2.fill(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
                g2.setPaint(fg);
                //g2.drawString("Filled Rectangle2D", x, stringY);
                x += gridWidth;

            } else {
                System.out.print("O");

                // draw Rectangle2D.Double
                g2.setStroke(stroke);
                g2.draw(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
                //g2.drawString("Rectangle2D", x, stringY);
                x += gridWidth;
            }
            hourInDay--;
        }

        System.out.println();
        // NEW ROW
        x = 5;
        y += gridHeight;
        stringY += gridHeight;

        // checks minutes
        int on = minute % 5; // last 4 minutes
        int checkOn = (minute - on) / 5;
        for (int i = 0; i < 11; i++) {
            if (checkOn-- > 0) {
                if ((i + 1) % 3 == 0) {
                    System.out.print("R");

                    // fill Rectangle2D.Double (red)
                    g2.setPaint(red);
                    g2.fill(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
                    g2.setPaint(fg);
                    //g2.drawString("Filled Rectangle2D", x, stringY);
                    x += gridWidth;

                } else {
                    System.out.print("Y");

                    // fill Rectangle2D.Double (red)
                    g2.setPaint(yellow);
                    g2.fill(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
                    g2.setPaint(fg);
                    //g2.drawString("Filled Rectangle2D", x, stringY);
                    x += gridWidth;
                }
            } else {
                System.out.print("O");

                // draw Rectangle2D.Double
                g2.setStroke(stroke);
                g2.draw(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
                //g2.drawString("Rectangle2D", x, stringY);
                x += gridWidth;
            }
        }

        System.out.println();
        // NEW ROW
        x = 5;
        y += gridHeight;
        stringY += gridHeight;

        for (int i = 0; i < 4; i++) {
            if (on-- > 0) {
                System.out.print("R");

                // fill Rectangle2D.Double (red)
                g2.setPaint(yellow);
                g2.fill(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
                g2.setPaint(fg);
                //g2.drawString("Filled Rectangle2D", x, stringY);
                x += gridWidth;

            } else {
                System.out.print("O");

                // draw Rectangle2D.Double
                g2.setStroke(stroke);
                g2.draw(new Rectangle2D.Double(x, y, rectWidth, rectHeight));
                //g2.drawString("Rectangle2D", x, stringY);
                x += gridWidth;
            }
        }

        System.out.println();
        // NEW ROW
        x = 5;
        y += gridHeight;
        stringY += gridHeight;
        System.out.println();
        // NEW ROW
        x = 5;
        y += gridHeight;
        stringY += gridHeight;

    }

}
