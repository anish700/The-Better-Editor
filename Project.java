package JavaProject;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.text.*;
import static javax.swing.text.StyleConstants.*;

public class Project extends JFrame implements ActionListener,MouseListener
{   String selectedText;
    String fileName;
    JTextPane editor;
    JFileChooser fileChooser;
    JLabel      countLabel;
    int charCount;
    int wordCount;
    JPopupMenu rightClick;
    //declaring all necessary variables
    public Project()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }//sets the UI to look like the current windows theme

        selectedText = "";
        fileName = "c:\\Users";
        fileChooser = new JFileChooser(fileName);
        charCount = 0;
        wordCount = 0;
        //initializing variables
        setTitle("The Better Notepad");
        setLayout(new BorderLayout());

        JMenuBar    menuBar = new JMenuBar();

        JMenu       fileMenu = new JMenu("File");
        JMenu       editMenu = new JMenu("Edit");
        JMenu       formatButton = new JMenu("Customize");//declaring toolbar components
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatButton);
        //adding components to toolbar

        JMenuItem   newButton = new JMenuItem("New");
        JMenuItem   openButton = new JMenuItem("Open");
        JMenuItem   saveButton = new JMenuItem("Save");

        fileMenu.add(newButton);
        fileMenu.add(openButton);
        fileMenu.add(saveButton);//adding components of file menu
        newButton.addActionListener(this);
        openButton.addActionListener(this);
        saveButton.addActionListener(this);//adding action listeners to menu buttons
        newButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK));
        openButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK));
        saveButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK));//adding accelerators

        JMenuItem   copyButton = new JMenuItem("Copy");
        JMenuItem   cutButton = new JMenuItem("Cut");
        JMenuItem   pasteButton = new JMenuItem("Paste");
        JMenuItem   searchButton = new JMenuItem("Search");

        editMenu.add(copyButton);
        editMenu.add(cutButton);
        editMenu.add(pasteButton);
        editMenu.addSeparator();
        editMenu.add(searchButton);//adding components of edit menu

        copyButton.addActionListener(this);
        cutButton.addActionListener(this);
        pasteButton.addActionListener(this);
        searchButton.addActionListener(this);

        copyButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK));
        cutButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,Event.CTRL_MASK));
        pasteButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,Event.CTRL_MASK));
        searchButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,Event.CTRL_MASK));//adding accelerators

        JMenuItem   fontButton = new JMenuItem("Font");
        JMenuItem   shapesButton = new JMenuItem("Play with figures");
        formatButton.add(fontButton);
        formatButton.addSeparator();
        formatButton.add(shapesButton);
        fontButton.addActionListener(this);
        shapesButton.addActionListener(this);//adding action listeners to menu buttons

        JMenuItem   rightCopy = new JMenuItem("Copy");
        JMenuItem   rightCut = new JMenuItem("Cut");
        JMenuItem   rightPaste = new JMenuItem("Paste");
        JMenuItem   rightFont = new JMenuItem("Font");
        rightCopy.addActionListener(this);
        rightCut.addActionListener(this);
        rightPaste.addActionListener(this);
        rightFont.addActionListener(this);
        rightClick = new JPopupMenu();
        rightClick.add(rightCopy);
        rightClick.add(rightCut);
        rightClick.add(rightPaste);
        rightClick.addSeparator();
        rightClick.add(rightFont);
        //adding a right click menu

        countLabel = new JLabel("Selected Words = "+wordCount+" Selected Characters = "+charCount);
        add(countLabel,BorderLayout.SOUTH);//counting selected words and characters

        add(menuBar,BorderLayout.NORTH);//adding the menu bar

        editor = new JTextPane();
        editor.addMouseListener(this);

        JScrollPane scrollPane = new JScrollPane(editor);
        add(scrollPane);
        //creating the editor and adding a scroll bar
        pack();
        //pack all components to preferred sizes


    }
    public void actionPerformed(ActionEvent e)
    {
        switch(e.getActionCommand())
        {
            case "New":editor.setText("");break;
            case "Open":if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)//check if ok button has been hit
            {
                fileName = fileChooser.getSelectedFile().getAbsolutePath();//get name of file from the file chooser
                try
                {
                    FileReader f = new FileReader(fileName);
                    editor.read(f, null);//reading from file into the editor
                    f.close();//closing the file
                }
                catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(null, "File not found");
                }

            }break;
            case "Save":if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
            {
                fileName = fileChooser.getSelectedFile().getAbsolutePath();//same as above
                File f1 = new File(fileName);//create new file with input name
                if(f1.exists())
                {}
                else
                    try
                    {
                        f1.createNewFile();
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Unexpected Error Occured");
                    }

                try
                {
                    FileWriter f = new FileWriter(fileName);

                    editor.write(f);//writing to the file
                    f.close();
                }
                catch (IOException ex)
                {
                }

            }break;
            case "Copy": editor.copy();break;
            case "Cut": editor.cut();break;
            case "Paste": editor.paste();break;//cut, copy, paste functions
            case "Search": Search searchObject = new Search(editor);break;//creates object of search class to use search functions
            case "Font": setFont y = new setFont(editor);break;//creates object of setFont class to use font functions
            case "Play with figures": Shapes o = new Shapes();//creates object of shapes class to use shape functions
        }

    }

    public static void main(String[] args)
    {   Project projectNew = new Project();
        projectNew.setVisible(true);
        projectNew.setSize(1000,1000);
        projectNew.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        projectNew.setLocationRelativeTo(null);//create main frame and set properties

    }


    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e)
    {
        if(e.getModifiers()== MouseEvent.BUTTON3_MASK)
            rightClick.show(editor, e.getX(), e.getY());//activates menu on right click
    }

    public void mouseReleased(MouseEvent e)
    {
        wordCount = 0;
        charCount = 0;
        count();
        countLabel.setText("Selected Words = "+wordCount+" Selected Characters = "+charCount);//calls fucntions to calculate words and characters

    }


    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void count()
    {
        LinkedList      list = new LinkedList();
        try
        {
            StringTokenizer string = new StringTokenizer(editor.getSelectedText(),"\\.|,| |;|:|\\?|\\(|\\)|\\'");
            while(string.hasMoreElements())
            {
                list.add(string.nextToken());

            }
            wordCount = list.size();
            for(int i = 0;i<list.size();i++)
            {
                charCount += list.get(i).toString().length();
            }//count function to calculate selected words and characters
        }
        catch(NullPointerException e)
        {
            wordCount = 0;
            charCount = 0;
        }

    }


}
class Shapes extends Canvas implements ActionListener,MouseMotionListener,MouseListener
{
    JPanel p1, p2;JFrame f;
    int shape,shapeDrawn;
    int  drawX,drawY,initX,initY,offX,offY;
    JRadioButton b1, b2, b3, b4, b5;
    JLabel fgColorName;
    JLabel bgColorName;
    JComboBox shapes;
    Color foreColor;
    Color backColor;
    //create variables

    public Shapes()
    {
        f = new JFrame("Shapes");
        init();//initialize values of shapes
        p1 = new JPanel(new GridLayout(2,3));
        foreColor = Color.BLACK;
        backColor = Color.WHITE;
        //initialize variables


        p1.setOpaque(true);
        p1.setBackground(Color.white);

        setBackground(Color.WHITE);
        //setting colors of components
        String[]    shapeList = {"rectangle","oval","circle","square","equilateral triangle"};
        shapes = new JComboBox(shapeList);
        shapes.addActionListener(this);
        JLabel shapeName = new JLabel("Shape:");
        shapeName.setOpaque(true);
        fgColorName = new JLabel("Foreground");
        fgColorName.setOpaque(true);
        bgColorName = new JLabel("Background");
        bgColorName.setOpaque(true);
        p1.add(shapeName);
        p1.add(fgColorName);
        p1.add(bgColorName);
        JButton fgColor = new JButton("Change foreground");
        JButton bgColor = new JButton("Change background");
        fgColor.addActionListener(this);
        bgColor.addActionListener(this);
        p1.add(shapes);
        p1.add(fgColor);
        p1.add(bgColor);
        //creating components, adding them to a panel
        JPanel  labels = new JPanel(new GridLayout(2,1));
        labels.add(new JLabel("Choose your preferred shape, foreground color and background color"));
        labels.add(new JLabel("Drag on red circle to move object, drag elsewhere to alter size"));
        //adding labels to direct what to do
        addMouseListener(this);
        addMouseMotionListener(this);
        f.setLayout(new BorderLayout());
        f.add(p1,BorderLayout.SOUTH);
        f.add(labels,BorderLayout.NORTH);
        //adding components to main frame
        f.add(this);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setSize(500,500);
    }
    void init()
    {
        shapeDrawn = 0;
        drawX = 0;
        drawY = 0;
        initX = 0;
        initY = 0;
        offX = 50;
        offY = 50;
        //initialize shape variables
    }


    public void actionPerformed(ActionEvent e)
    {

        switch(e.getActionCommand())
        {
            case "Change foreground":
                fgColorName.setBackground(JColorChooser.showDialog(this,"Select a color", foreColor));
                foreColor = fgColorName.getBackground();break;
            case "Change background":
                bgColorName.setBackground(JColorChooser.showDialog(this,"Select a color", foreColor));
                backColor = bgColorName.getBackground();break;
        }//using JColorChooser to get foreground or background color depending on button pressed
        repaint();//repaints the canvas
    }

    public void paint(Graphics g)
    {    this.setBackground(backColor);
        int[] triXPts = {drawX,  drawX-(int)Math.sqrt(3)*Math.max(Math.abs(offX), Math.abs(offY)),drawX+(int)Math.sqrt(3)*Math.max(Math.abs(offX), Math.abs(offY)),drawX};
        int[] triYPts = {drawY-Math.max(Math.abs(offX), Math.abs(offY)),drawY+Math.max(Math.abs(offX), Math.abs(offY))/2, drawY+Math.max(Math.abs(offX), Math.abs(offY))/2,drawY-Math.max(Math.abs(offX), Math.abs(offY))};
        //creating triangle coordinates in terms of initial values
        g.setColor(foreColor);//set color chosen
        switch(shape)
        {
            case 1: g.drawRect(drawX-Math.abs(offX/2), drawY-Math.abs(offY/2), Math.abs(offX), Math.abs(offY));break;
            case 2: g.drawOval(drawX-Math.abs(offX/2), drawY-Math.abs(offY/2), Math.abs(offX), Math.abs(offY));break;
            case 3: g.drawOval(drawX-Math.max(Math.abs(offX), Math.abs(offY))/2, drawY-Math.max(Math.abs(offX), Math.abs(offY))/2, Math.max(Math.abs(offX), Math.abs(offY)),Math.max(Math.abs(offX), Math.abs(offY)));break;
            case 4: g.drawRect(drawX-Math.max(Math.abs(offX), Math.abs(offY))/2, drawY-Math.max(Math.abs(offX), Math.abs(offY))/2, Math.max(Math.abs(offX), Math.abs(offY)),Math.max(Math.abs(offX), Math.abs(offY)));break;
            case 5: g.drawPolygon(triXPts, triYPts, 4);
                //draw shape that is chosen
        }

        if(shape!=0)
        {
            g.setColor(Color.red);

            g.drawOval(drawX-5, drawY-5, 10, 10);
            g.setColor(foreColor);
            //draw the centre circle to move the shape
        }
    }



    public void mouseDragged(MouseEvent e)
    {
        if(Math.abs(e.getX()-drawX)<10&&Math.abs(e.getY()-drawY)<10)
        {
            drawX +=e.getX()-initX;
            drawY += e.getY()-initY;//changes location of shape if the mouse is inside red circle
        }
        else
        {


            if(Math.abs(initX-drawX)<Math.abs(e.getX()-drawX))
                offX+=Math.abs(e.getX()-initX);
            else
                offX-=Math.abs(e.getX()-initX);
            if(Math.abs(initY-drawY)<Math.abs(e.getY()-drawY))
                offY+=Math.abs(e.getY()-initY);
            else
                offY-=Math.abs(e.getY()-initY);

        }//changes offsets of shape if mouse is dragged outside red circle
        repaint();
        initX =e.getX();
        initY =e.getY();


    }


    public void mouseMoved(MouseEvent e) {}


    public void mouseClicked(MouseEvent e) {}


    public void mousePressed(MouseEvent e)
    {
        if(shape!= shapes.getSelectedIndex()+1)
        {   shape = shapes.getSelectedIndex()+1;
            init();
        }
        if(shapeDrawn == 0)
        {
            shapeDrawn = 1;
            drawX = e.getX();
            drawY = e.getY();
            repaint();
        }
        initX = e.getX();
        initY = e.getY();//initial shape drawn when the shape is chosen


    }


    public void mouseReleased(MouseEvent e) {}



    public void mouseEntered(MouseEvent e) {}


    public void mouseExited(MouseEvent e) {}


}

class Search extends JFrame implements ActionListener
{
    JTextPane editor;
    JTextField  findField;
    JTextField  replaceField;
    int         currIndex;
    public Search(JTextPane editorPane)
    {               currIndex = 0;
        editor = editorPane;
        setLayout(new BorderLayout(3,3));
        JPanel      p1 = new JPanel();
        JPanel      p2 = new JPanel();
        JPanel      p3= new JPanel();
        p1.setLayout(new GridLayout(3,1));
        p2.setLayout(new GridLayout(3,1));
        p3.setLayout(new GridLayout(3,1));//creating panels

        JLabel      l1 = new JLabel("Find: ");
        JLabel      l2 = new JLabel("Replace: ");
        findField = new JTextField();
        replaceField = new JTextField();
        JButton     b1 = new JButton("Find Next");
        JButton     b2 = new JButton("Replace");
        JButton     b3 = new JButton("Replace All");//creating components

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);//adding action listeners to buttons

        p1.add(l1);
        p1.add(l2);
        add(p1,BorderLayout.WEST);
        p2.add(findField);
        p2.add(replaceField);
        add(p2,BorderLayout.CENTER);
        p3.add(b1);
        p3.add(b2);
        p3.add(b3);
        add(p3,BorderLayout.EAST);//adding components to frame

        setVisible(true);
        setLocationRelativeTo(editor);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300,150);

    }
    public void findNext()
    {
        currIndex = editor.getText().toLowerCase().replaceAll("[\\n\\t]","").indexOf(findField.getText().toLowerCase(), currIndex);
        //find current index including delimiters
        if(currIndex==-1)
        {
            JOptionPane.showMessageDialog(null, "Could not find \""+findField.getText()+"\"");
            currIndex = 0;
        }
        else
        {
            editor.select(currIndex, currIndex+findField.getText().length());
            currIndex+=findField.getText().length();
        }//selects the text and moves to the next index
    }
    public void replace()
    {
        if(editor.getSelectedText()==null)
        {
            findNext();
        }

        if(editor.getSelectedText()!=null)
        {   editor.replaceSelection(replaceField.getText());
            currIndex=currIndex-findField.getText().length()+replaceField.getText().length();
        }//calls find function and replaces selected text
    }
    public void replaceAll()
    {
        String editorText = editor.getText().toLowerCase().replaceAll("[\\n\\t]","");
        if(editorText.contains(findField.getText().toLowerCase())==false)
        {
            JOptionPane.showMessageDialog(null, "Could not find \""+findField.getText()+"\"");
        }
        editorText = editorText.replace(findField.getText().toLowerCase(), replaceField.getText());
        editor.setText(editorText);
        //separate functions used to replace all instances of word

    }
    public void actionPerformed(ActionEvent e)
    {
        switch(e.getActionCommand())
        {
            case "Find Next": findNext();break;
            case "Replace": replace();break;
            case "Replace All": replaceAll();break;//calls respective funtions
        }
    }

}

class setFont extends JFrame implements ActionListener
{
    JTextPane editor;
    StyledDocument doc;
    JComboBox   fontSelect;
    JComboBox   sizeSelect;
    JCheckBox   bold;
    JCheckBox   italics;
    JCheckBox    upperCase;
    JCheckBox    lowerCase;
    JButton     ok;

    //declare variables
    public setFont(JTextPane e)
    {
        editor = e;
        doc = editor.getStyledDocument();
        setName("Font");
        String[]    fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String[]    sizes = {"9","10","11","12","14","16","18","20","22","24","28","36","48","72"};
        //getting all avaliable fonts and setting some standard sizes
        JPanel      p = new JPanel();
        p.setLayout(new GridLayout(6,2));
        p.add(new JLabel("Font:"));
        p.add(new JLabel("Size:"));
        fontSelect = new JComboBox(fonts);
        p.add(fontSelect);
        sizeSelect = new JComboBox(sizes);

        p.add(sizeSelect);


        p.add(new JLabel("Style:"));
        p.add(new JLabel());
        bold = new JCheckBox("Bold");
        italics = new JCheckBox("Italics");
        upperCase = new JCheckBox("Uppercase");
        lowerCase = new JCheckBox("Lowercase");
        p.add(bold);
        p.add(upperCase);
        p.add(italics);
        p.add(lowerCase);
        ButtonGroup bg = new ButtonGroup();
        bg.add(upperCase);
        bg.add(lowerCase);
        ok = new JButton("Ok");
        ok.addActionListener(this);
        p.add(ok);
        p.add(new JLabel());
        //adding all components used to change font attributes and adding uppercase and lowercase to a group to remove ambiguity
        add(p);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(editor);
        setSize(300,220);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e)
    {   int size = editor.getFont().getSize();
        int style = Font.PLAIN;
        if (bold.isSelected() && italics.isSelected())
            style = Font.BOLD | Font.ITALIC;
        else if (bold.isSelected())
            style = Font.BOLD;
        else if (italics.isSelected())
            style = Font.ITALIC;
        //sets font style if chosen
        String  font = fontSelect.getSelectedItem().toString();//gets font chosen

        try
        {
            size = Integer.parseInt(sizeSelect.getSelectedItem().toString());//size of font
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Invalid Size");
        }
        if(editor.getSelectedText()==null)
        {
            Font    f = new Font(font,style,size);
            editor.setFont(f);

        }//sets font of whole editor if no text is selected
        else
        {

            MutableAttributeSet  fontStyle = editor.getInputAttributes();

            setFontFamily(fontStyle, font);
            setFontSize(fontStyle, size);
            setBold(fontStyle, bold.isSelected());
            setItalic(fontStyle, italics.isSelected());
            //creates a new font style and adds required attributes
            String  selectedText = editor.getSelectedText();
            int     selectStart = editor.getSelectionStart(),selectEnd = editor.getSelectionEnd();
            if(upperCase.isSelected())
                selectedText = selectedText.toUpperCase();
            if(lowerCase.isSelected())
                selectedText = selectedText.toLowerCase();
            editor.replaceSelection(selectedText);//replaces selected text with uppercase or lowercase if chosen
            editor.select(selectStart, selectEnd);
            editor.getStyledDocument().setCharacterAttributes(editor.getSelectionStart(),editor.getSelectionEnd()-editor.getSelectionStart(),fontStyle, false);
            //sets attributes of selected text based on font style

        }


    }
}