import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinesFinder extends JFrame {
    private JPanel painelPrincipal;
    private JLabel nomeDoJogo;
    private JPanel Oeste;
    private JButton jogoFácilButton;
    private JButton jogoMédioButton;
    private JButton jogoDifícilButton;
    private JButton sairButton;

    public MinesFinder(String title) {
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelPrincipal);
        pack();
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jogoFácilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                var janela = new JanelaPrincipal("Jogo Facil",new CampoMinado(9,9, 10));

                janela.setVisible(true);


            }
        });
        jogoMédioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        jogoDifícilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
    }
    public static void main(String[] args) {
        new MinesFinder("Mines Finder").setVisible(true);
    }
}