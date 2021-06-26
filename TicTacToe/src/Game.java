import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game {
    final int NUM_BOARD_SLOTS = 9;
    final String COMPUTER_WIN = "COMPUTER WINS";
    final String PLAYER_WIN = "PLAYER WINS!!!";
    JFrame frame = new JFrame();
    JLayeredPane layeredPane = new JLayeredPane();
    JLabel titleLabel = new JLabel();
    JLabel instructions = new JLabel();
    JLabel padding = new JLabel();
    JRadioButton computer = new JRadioButton();
    JRadioButton player = new JRadioButton();
    JButton startGame = new JButton();
    boolean playerStart = true;
    boolean choice = true;
    JButton[] gameBtns = new JButton[9];
    JLayeredPane buttonPanel = new JLayeredPane();

    public Game(){
        DefineInitialLayout();
        AddInitialComponents();
        SetFrame();
        /**
         * listeners check for presses
         * when a radio button is selected the proper value is set
         * either the player or computer is selected to play first
         * when the player is selected the computer is deselected
         * and vice versa
         * when the start button is pressed the game begins
         */
        computer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(computer.isSelected()){
                    player.setSelected(false);
                    playerStart = false;
                    choice = false;
                }
                else{
                    player.setSelected(true);
                    playerStart = true;
                    choice = true;
                }
            }
        });
        player.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(player.isSelected()){
                    computer.setSelected(false);
                    playerStart = true;
                    choice = true;
                }
                else{
                    computer.setSelected(true);
                    playerStart = false;
                    choice = false;
                }
            }
        });
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InitializeGame();
            }
        });
    }

    /**
     * Here I set all of the initial attributes for the layout components
     */
    private void DefineInitialLayout(){
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(65,135,245));
        titleLabel.setText("TIC-TAC-TOE");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.TOP);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 55));
        titleLabel.setBounds(0,0,500,100);
        instructions.setOpaque(true);
        instructions.setBackground(new Color(65,135,245));
        instructions.setText("   Select who plays first:");
        instructions.setHorizontalAlignment(JLabel.LEFT);
        instructions.setBounds(0,100,500,50);
        computer.setText("Computer");
        computer.setBounds(0,200,500,50);
        computer.setOpaque(true);
        computer.setBackground(new Color(65,135,245));
        player.setText("Player");
        player.setSelected(true);
        player.setBounds(0,150,500,50);
        player.setOpaque(true);
        player.setBackground(new Color(65,135,245));
        startGame.setText("Start Game");
        startGame.setBounds(0,250,500,150);
        startGame.setOpaque(true);
        startGame.setBackground(new Color(65,135,245));
        padding.setOpaque(true);
        padding.setBackground(new Color(65,135,245));
        padding.setBounds(0,400,500,100);
        layeredPane.setBounds(0,0,500,500);
    }

    /**
     * Here I Create the buttons that serve as the positions for the game
     * I remove the previous menu layout and create the game layout
     */
    private void InitializeGame(){
        frame.getContentPane().removeAll();
        frame.getContentPane().invalidate();
        frame.getContentPane().add(buttonPanel);
        frame.getContentPane().revalidate();
        frame.add(padding);
        buttonPanel.setBounds(0,100,500,350);
        buttonPanel.setLayout(new GridLayout(3,3));
        buttonPanel.setVisible(true);
        buttonPanel.setBackground(new Color(65,135,245));
        buttonPanel.removeAll();
        /**
         * Here I initialize the buttons to be blank
         */
        for(int i = 0; i < NUM_BOARD_SLOTS; i++){
            gameBtns[i] = new JButton();
            gameBtns[i].setText(" ");
            gameBtns[i].setOpaque(true);
            gameBtns[i].setVisible(true);
            gameBtns[i].setBackground(new Color(65,135,245));
            int finalI = i;
            gameBtns[i].addActionListener(new ActionListener() {
                /**
                 * When a button is clicked by the user this action takes place
                 * The action checks that the clicked button has not been taken
                 * if the button has been taken, nothing happens and the player is
                 * free to choose again until a valid button is clicked
                 * When a proper input is detected the buton is given its mark
                 * and the computer gets its turn
                 * @param e
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(playerStart){
                        if(gameBtns[finalI].getText().equals(" ")){
                            gameBtns[finalI].setFont(new Font("Arial", Font.BOLD, 50));
                            gameBtns[finalI].setText("X");
                            playerStart = false;
                        }
                        /**
                         * Here I check for either a player win or a draw
                         * If both checks are false I give the computer a turn
                         * if either is true the win layout is called from the
                         * CheckForWin or CheckForDraw accordingly
                         */
                        if(CheckForWin("X") || CheckForDraw()){

                        }
                        else{
                            TakeTurn();
                        }
                    }
                }
            });
            buttonPanel.add(gameBtns[i], Integer.valueOf(3));
        }
        TakeTurn();
    }

    /**
     * This function lets the computer take a turn after the players turn
     * It is also possible for the computer to move first
     * The computer chooses a free space at random
     */
    private void TakeTurn(){
        Random rand = new Random();
        int upperBound = 9;
        if(!playerStart){
            int compPick = rand.nextInt(upperBound);
            while(gameBtns[compPick].getText().equals("O") || gameBtns[compPick].getText().equals("X")) {
                compPick = rand.nextInt(upperBound);
                CheckForDraw();
            }
            gameBtns[compPick].setFont(new Font("Arial", Font.BOLD, 50));
            gameBtns[compPick].setText("O");
            playerStart = true;
        }
        if(!CheckForWin("O") || CheckForDraw()){

        }
    }

    /**
     * AddInitialComponents and SetFrame are called on the menu layout
     * before a game starts
     */
    private void AddInitialComponents(){
        layeredPane.add(titleLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(instructions, Integer.valueOf(1));
        layeredPane.add(computer, Integer.valueOf(1));
        layeredPane.add(player, Integer.valueOf(1));
        layeredPane.add(startGame, Integer.valueOf(1));
        layeredPane.add(padding, Integer.valueOf(1));
        layeredPane.add(buttonPanel, Integer.valueOf(2));
    }
    private void SetFrame(){
        frame.add(layeredPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    /**
     * checks for horizontal and vertical win cases as well as diagonal
     * I wanted to have a pause where I highlight the winning row/column/diagonal and
     * change the color but I did not have time
     * @param mark
     * @return
     */
    private boolean CheckForWin(String mark) {
        if(gameBtns[0].getText().equals(mark) && gameBtns[1].getText().equals(mark) && gameBtns[2].getText().equals(mark)){
            if(mark.equals("O")){
                MakeWinScreen(COMPUTER_WIN);
            }
            else{
                MakeWinScreen(PLAYER_WIN);
            }
            return true;
        }
        if(gameBtns[3].getText().equals(mark) && gameBtns[4].getText().equals(mark) && gameBtns[5].getText().equals(mark)){
            if(mark.equals("O")){
                MakeWinScreen(COMPUTER_WIN);
            }
            else{
                MakeWinScreen(PLAYER_WIN);
            }
            return true;
        }
        if(gameBtns[6].getText().equals(mark) && gameBtns[7].getText().equals(mark) && gameBtns[8].getText().equals(mark)){
            if(mark.equals("O")){
                MakeWinScreen(COMPUTER_WIN);
            }
            else{
                MakeWinScreen(PLAYER_WIN);
            }
            return true;
        }
        if(gameBtns[0].getText().equals(mark) && gameBtns[3].getText().equals(mark) && gameBtns[6].getText().equals(mark)){
            if(mark.equals("O")){
                MakeWinScreen(COMPUTER_WIN);
            }
            else{
                MakeWinScreen(PLAYER_WIN);
            }
            return true;
        }
        if(gameBtns[1].getText().equals(mark) && gameBtns[4].getText().equals(mark) && gameBtns[7].getText().equals(mark)){
            if(mark.equals("O")){
                MakeWinScreen(COMPUTER_WIN);
            }
            else{
                MakeWinScreen(PLAYER_WIN);
            }
            return true;
        }
        if(gameBtns[2].getText().equals(mark) && gameBtns[5].getText().equals(mark) && gameBtns[8].getText().equals(mark)){
            if(mark.equals("O")){
                MakeWinScreen(COMPUTER_WIN);
            }
            else{
                MakeWinScreen(PLAYER_WIN);
            }
            return true;
        }
        if(gameBtns[0].getText().equals(mark) && gameBtns[4].getText().equals(mark) && gameBtns[8].getText().equals(mark)){
            if(mark.equals("O")){
                MakeWinScreen(COMPUTER_WIN);
            }
            else{
                MakeWinScreen(PLAYER_WIN);
            }
            return true;
        }
        if(gameBtns[2].getText().equals(mark) && gameBtns[4].getText().equals(mark) && gameBtns[6].getText().equals(mark)){
            if(mark.equals("O")){
                MakeWinScreen(COMPUTER_WIN);
            }
            else{
                MakeWinScreen(PLAYER_WIN);
            }
            return true;
        }
        return false;
    }

    /**
     * Here the program checks all spaces
     * if all spaces are filled and a win has not yet been declared
     * then the program declares a draw and the win layout is called accordingly
     * @return
     */
    private boolean CheckForDraw(){
        int checker = 0;
        for(int i = 0; i < NUM_BOARD_SLOTS; i++){
            if(!gameBtns[i].getText().equals(" ")){
                checker++;
            }
        }
        if(checker == 9){
            String draw = "DRAW!";
            MakeWinScreen(draw);
            return true;
        }
        return false;
    }

    /**
     * Here is where I remove the game panel and create the win layout
     * The win layout either displays a computer win or draw in black
     * The win layout may also display a player win in gold
     * The option to replay or exit is given
     * On replay a new game starts immediately
     * On exit a new window opens that has a menu screen
     * I was unable to fix this issue in the allotted time but
     * what should happen is when exit(menu) is clicked the game returns
     * to the menu screen in the same window not a new window
     * @param winner
     */
    private void MakeWinScreen(String winner){
        JPanel winPanel = new JPanel();
        JLabel playAgain = new JLabel();
        JButton playYes = new JButton();
        JButton playNo = new JButton();
        frame.getContentPane().removeAll();
        frame.getContentPane().invalidate();
        frame.getContentPane().add(winPanel);
        frame.getContentPane().revalidate();
        winPanel.setBounds(0,100,500,400);
        winPanel.setLayout(null);
        winPanel.setVisible(true);
        winPanel.setBackground(new Color(65,135,245));
        JLabel winLabel = new JLabel();
        winLabel.setOpaque(true);
        winLabel.setText(winner);
        winLabel.setHorizontalAlignment(JLabel.CENTER);
        winLabel.setVerticalAlignment(JLabel.TOP);
        winLabel.setBounds(0,50,500,100);
        winLabel.setBackground(new Color(65,135,245));
        winLabel.setFont(new Font("Arial", Font.BOLD, 50));
        if(winner.equals(PLAYER_WIN)){
            winLabel.setForeground(new Color(205,150,50));
        }
        else {
            winLabel.setForeground(Color.BLACK);
        }
        playAgain.setOpaque(true);
        playAgain.setText("Play Again?");
        playAgain.setHorizontalAlignment(JLabel.CENTER);
        playAgain.setVerticalAlignment(JLabel.BOTTOM);
        playAgain.setBounds(0,115,500, 50);
        playAgain.setBackground(new Color(65,135,245));
        playAgain.setForeground(Color.BLACK);

        playYes.setText("Play Again");
        playYes.setBounds(20,240, 200,125);
        playYes.setOpaque(true);
        playYes.setBackground(new Color(65,135,245));

        playNo.setText("Exit(menu)");
        playNo.setBounds(270,240, 200,125);
        playNo.setOpaque(true);
        playNo.setBackground(new Color(65,135,245));

        winPanel.add(winLabel);
        winPanel.add(playAgain);
        winPanel.add(playYes);
        winPanel.add(playNo);
        playYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                winPanel.setVisible(false);
                playerStart = choice;
                InitializeGame();
            }
        });
        playNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game newGame = new Game();
            }
        });
    }
}
