import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaPrincipal extends JFrame {
    private JPanel painelJogo; // painel do jogo. O nome é definido no modo Design, em "field name"

    private BotaoCampoMinado[][] botoes;
    private CampoMinado campoMinado;


    public JanelaPrincipal(String titulo,CampoMinado campoMinado) {

        super(titulo);

        this.campoMinado = campoMinado;

        var nrLinhas = campoMinado.getNrLinhas();

        var nrColunas = campoMinado.getNrColunas();

        this.botoes = new BotaoCampoMinado[nrLinhas][nrColunas];

        painelJogo.setLayout(new GridLayout(nrLinhas,nrColunas));

        // Criar e adicionar os botões à janela
        for (int linha = 0; linha < nrLinhas; ++linha) {
            for (int coluna = 0; coluna < nrColunas; ++coluna) {
                botoes[linha][coluna] = new BotaoCampoMinado(linha,coluna);
                painelJogo.add(botoes[linha][coluna]);

                botoes[linha][coluna].addActionListener(this::btnCampoMinadoActionPerformed);
                painelJogo.add(botoes[linha][coluna]);
            }
        }


        setContentPane(painelJogo);
        // Destrói esta janela, removendo-a completamente da memória.

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();
        setVisible(true); // opcional. Pode optar por fazer depois.


    }

    private void btnCampoMinadoActionPerformed(ActionEvent e) {

        var botao = (BotaoCampoMinado) e.getSource();

        var x = botao.getLinha();
        var y = botao.getColuna();

        campoMinado.revelarQuadricula(x, y);
        actualizarEstadoBotoes();

        //PG 27

    }

    private void actualizarEstadoBotoes() {
        for (int x = 0; x < campoMinado.getNrLinhas(); x++) {
            for (int y = 0; y < campoMinado.getNrColunas(); y++) {
                botoes[x][y].setEstado(campoMinado.getEstadoQuadricula(x, y));
            }
        }
    }

}
