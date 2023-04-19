import java.sql.SQLOutput;
import java.util.Random;

public class CampoMinado {

    private boolean[][] minas;

    public static final int VAZIO = 0;

    /* de 1 a 8 são o número de minas à volta */
    public static final int TAPADO = 9;
    public static final int DUVIDA = 10;
    public static final int MARCADO = 11;
    public static final int REBENTADO = 12;

    private int[][] estado;

    private int nrLinhas; // ou largura
    private int nrColunas; // ou altura
    private int nrMinas;


    private long instanteInicioJogo;
    private long duracaoJogo;

    private boolean primeiraJogada = true;

    private boolean jogoGanho;
    private boolean jogadorDerrotado;

    public CampoMinado(int nrLinhas, int nrColunas, int nrMinas) {

        this.nrLinhas = nrLinhas;
        this.nrColunas = nrColunas;
        this.nrMinas = nrMinas;

        this.minas = new boolean[nrLinhas][nrColunas];
        this.estado = new int[nrLinhas][nrColunas];

        this.jogoGanho = false;
        this.jogadorDerrotado = false;

        for (var x = 0; x < nrLinhas; ++x) {
            for (var y = 0; y < nrColunas; ++y) {
                estado[x][y] = TAPADO;
            }
        }

    }

    private void colocarMinas(int exceptoX, int exceptoY) {
        var aleatorio = new Random();
        var x = 0;
        var y = 0;
        for (var i = 0; i < nrMinas; ++i) {
            do {
                x = aleatorio.nextInt(nrLinhas);
                y = aleatorio.nextInt(nrColunas);
            } while (minas[x][y] || (x == exceptoX && y == exceptoY));
            minas[x][y] = true;
        }
    }


    public void revelarQuadricula(int linha, int coluna) {
        if (jogoGanho ||estado[linha][coluna] < TAPADO) {
            return;
        }

        if (primeiraJogada) {
            primeiraJogada = false;
            colocarMinas(linha, coluna);

            instanteInicioJogo = System.currentTimeMillis();
        }

        if (minas[linha][coluna]) {
            estado[linha][coluna] = REBENTADO;
            jogadorDerrotado = true;
            jogoGanho = false;
            //store in gameDuration the time in milliseconds since the game started
            duracaoJogo = System.currentTimeMillis() - instanteInicioJogo;
            return;
        }



        //if cell is empty, change state to EMPTY
        if (contarMinasVizinhas(linha, coluna) == 0) {
            estado[linha][coluna] = VAZIO;
            revelarQuadriculasVizinhas(linha, coluna);
        } else {
            //if cell is not empty, change state to the number of mines in the surrounding cells
            estado[linha][coluna] = contarMinasVizinhas(linha, coluna);
        }

        //if all cells are revealed, set gameOver to true and inform the user that he won
        if (isVitoria()) {
            //debug printline to see if the game is won
            System.out.println("You won!");
            jogadorDerrotado = true;
            jogoGanho = true;
            //store in gameDuration the time in milliseconds since the game started
            duracaoJogo = System.currentTimeMillis() - instanteInicioJogo;
        }


    }

    public void revelarQuadriculasVizinhas(int linha, int coluna){

        for (var i = Math.max(0, linha - 1); i < Math.min(nrLinhas, coluna + 2); ++i) {
            for (var j = Math.max(0, coluna - 1); j < Math.min(nrColunas, coluna + 2); ++j)
            {
                revelarQuadricula(i, j);
            }
        }
    }



    public void marcarComoTendoMina(int linha, int coluna){
        if (estado[linha][coluna] == TAPADO || estado[linha][coluna] == DUVIDA) {
            estado[linha][coluna] = MARCADO;
        }

    }
    public void marcarComoSuspeita(int linha, int coluna){
        if (estado[linha][coluna] == TAPADO || estado[linha][coluna] == MARCADO) {
            estado[linha][coluna] = DUVIDA;
        }

    }

    public void desmarcarQuadricula(int linha, int coluna){
        if(estado[linha][coluna] == MARCADO || estado[linha][coluna] == DUVIDA ){
            estado[linha][coluna] = TAPADO;
        }

    }

    private int contarMinasVizinhas(int x, int y) {
        var numMinasVizinhas = 0;
        for (var i = Math.max(0, x - 1); i < Math.min(nrLinhas, x + 2); ++i) {
            for (var j = Math.max(0, y - 1); j < Math.min(nrColunas, y + 2); ++j)
            {
                if (minas[i][j]) {
                    ++numMinasVizinhas;
                }
            }
        }
        return numMinasVizinhas;
    }

    private boolean isVitoria() {
        for (int i = 0; i < nrLinhas; ++i) {
            for (var j = 0 ; j < nrColunas; ++j) {
                if (!minas[i][j] && estado[i][j] >= TAPADO) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isJogoGanho() {
        return jogoGanho;
    }

    public boolean isJogadorDerrotado(){
        return jogadorDerrotado;
    }

    public int getEstadoQuadricula(int x, int y) {
        return estado[x][y];
    }

    public boolean hasMina(int x, int y) {
        return minas[x][y];
    }

    public int getEstado(int linhas, int colunas) {
        return estado[linhas][colunas];
    }

    public long getDuracaoJogo() {
        if (primeiraJogada) {
            return 0;
        }
        if (!jogoGanho) {
            return System.currentTimeMillis() - instanteInicioJogo;
        }

        return duracaoJogo;
    }

    public int getNrLinhas() {
        return nrLinhas;
    }

    public int getNrColunas() {
        return nrColunas;
    }
}
