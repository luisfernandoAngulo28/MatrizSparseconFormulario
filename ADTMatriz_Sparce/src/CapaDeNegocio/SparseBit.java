package CapaDeNegocio;


/**
 *
 * @author hp
 */
public class SparseBit {

    VectorBitGen VFC;
    double VD[];
    int Nfil;
    int Ncol;
    float ep;
    int dim;
    int Nb;

    public SparseBit(int Nfil, int Ncol, float ep) {
        Nb = 1 + (int) (Math.log(Nfil * Ncol) / Math.log(2));
        VFC = new VectorBitGen(5, Nb);
        VD = new double[5];
        this.Nfil = Nfil;
        this.Ncol = Ncol;
        this.ep = (float) ep;
        this.dim = 0;
    }

    public void Set(int Fila, int Col, double valor) {

        if ((Fila > Nfil) || (Col > Ncol)) {
            System.out.println("Error: fila y columna fuera de rango");
            System.exit(1);
        } else {
            int FC = (Fila - 1) * Ncol + Col;// FC es el orden numerico del valor en dicha posicion
            int Posicion = Existe(FC);//devuelve -1 cuando no existe dicho elemento caso contrario devuelve la posicion
            if ((Posicion == -1) && (valor != ep)) {//insertar un nuevo valor
                Redimensionar();//alarga el vector de ser necesario
                dim++;
                VFC.Insertar(FC, dim + 1);
                VD[dim] = valor;
            } else if (valor != ep) {///cambiar el valor existente
                VD[Posicion] = valor;
            } else {//eliminar el valor porque es elemento predominante
                for (int i = Posicion; i < dim; i++) {
                    VFC.Insertar(VFC.Sacar(i + 2), i + 1);
                    VD[i] = VD[i + 1];
                }
                dim--;
            }
        }
    }
public double get(int Fila, int Col) {
        int FC = (Fila - 1) * Ncol + Col;
        int Posicion = Existe(FC);
        if (Posicion != -1) {
            return VD[Posicion];
        } else {
            return ep;
        }
    }
    public int Existe(int FC) {
        int i = 1;
        while ((i <= dim + 1) && (VFC.Sacar(i) != FC)) {
            i++;
        }
        if (i > dim + 1) {
            return -1;
        } else {
            return i - 1;
        }
    }

    public void Redimensionar() {
         if (dim == VD.length - 1) {
            int VFCA[] = new int[dim + 1];
            double VDA[] = new double[dim + 1];
            System.arraycopy(VD, 0, VDA, 0, VD.length);
            for (int i = 0;i<=dim;i++){
                VFCA[i]=VFC.Sacar(i+1);
            }
            VD = new double[dim + 11];
            VFC = new VectorBitGen(dim+12,Nb) ;
            System.arraycopy(VDA, 0, VD, 0, VDA.length);
            for (int i = 0;i<=dim;i++){
                VFC.Insertar(VFCA[i],i+1);
            }
        }

    }
     @Override
    public String toString() {
        String S = "";
        for (int i=1;i<=Nfil;i++){
            for (int j=1;j<=Ncol;j++){
                S=S+(Math.rint(get(i,j)*100)/100)+"  ";
            }
            S=S+"\n";
        }
        return S;
    }
    public static void main(String[] args) {
        SparseBit A = new SparseBit(5, 5, 0);
        System.out.println(A);
        for (int i=1;i<=A.Nfil;i++){
            for (int j=1;j<=A.Ncol;j++){
                A.Set(i, j,6*Math.random());
            }    
        }
         System.out.println(A);
         A.Set(2, 2, 0);
       //  A.Set(3, 3, 0);
        // A.Set(4, 3, 0);
         A.Set(5, 3, 0);
         System.out.println(A);
         A.Set(1, 1, 9.991);
         System.out.println(A);
         
         

    }

}
