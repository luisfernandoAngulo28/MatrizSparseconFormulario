package CapaDeNegocio;

/**
 *
 * @author hp
 */
public class VectorBitGen {
    int v[];
    int dim;
    int cbit;
    int maxval;
    //cantele es la cantidad de elementos del vector y cantbit es la cantidad de bits por elemento
    public VectorBitGen(int CantEle, int CantBit ) {
        int Nbit = CantEle * CantBit;// cantidad de bits necesarios
        int NE = Nbit / 32;    //cantidad de Enteros que se necesitan
        if (Nbit % 32 != 0) { // en caso que sobre bits fuera se necesita uno adicional
            NE++;
        }
        cbit=CantBit;
        dim = CantEle;
        maxval=((int)Math.pow(2,CantBit)-1);
        v = new int[NE];
    }
    
     public void Insertar(int ele, int pos) {// pos es la posicion del vector donde se insertar el valor
        if ((pos <= dim)&&(ele<=maxval)&&(pos>0))  {
            int Nbit = CNbit(pos);
            int NE = CNEnt(pos);
            int e = ele;
            e = e << (Nbit - 1);
            int mask = maxval;
            mask = mask << (Nbit - 1);
            mask = ~mask;
            v[NE] = v[NE] & mask;
            v[NE] = v[NE] | e;
            if ((Nbit-1 + cbit) > 32) {        /////falta, hemos perdido bits
                int bitf = Nbit - 1 + this.cbit - 32;       //////calculamos los bits que faltan
                int mask1 = (int) (Math.pow(2, bitf) - 1);///// creamos una maskara para los bits faltantes 
		int aux=ele;
                //////no es necesario mover dicha mascara, siempre estara al comienzo
                mask1 = ~mask1;//////mask para limpiar el espacio 
                v[NE + 1] = v[NE+1] & mask1;/////limpiando
                aux = aux >>> (this.cbit - bitf);////// movemos a la derecha sin signo, para meter en el espacio correspondiente
//                aux = aux&~mask1;
                v[NE + 1] = v[NE + 1] | aux;///metemos el valor que habiamos perdido :)
            }
        }
    }
	public void Intercambiar(int pos1,int pos2){
	int aux = Sacar(pos1);
        int valor=Sacar(pos2);
	Insertar(valor,pos1);
	Insertar(aux,pos2);
		
	}
    public int CNbit(int pos) {
        int Nbit = (((pos-1)*cbit) % 32)+1;
        return Nbit;
    }

    public int CNEnt(int pos) {
        int NEnt = ((pos-1)*cbit / 32);
        return NEnt;
    }
     public int Sacar(int pos) {
        int mask = maxval;//MASK con el mayor numero de bits posible 
        int Nbit = CNbit(pos);// ubicacion del bit 
        int NE = CNEnt(pos);
        mask = mask << (Nbit - 1);
        mask = mask & v[NE];
        mask = mask >>> (Nbit - 1);
        if ((Nbit-1+this.cbit)>32){
            int bitf=Nbit-1+this.cbit-32;//////calculamos los bits que faltan
            int mask1 = (int) (Math.pow(2, bitf) - 1);///// creamos una maskara para los bits faltantes 
                //////no es necesario mover dicha mascara, siempre estara al comienzo
            mask1=mask1&v[NE+1];
            mask1=mask1<<(this.cbit-bitf);
            mask=mask|mask1;
            }
    return (mask);
    }
    @Override
    public String toString() {
        String S= "V=[";
        for (int i=1;i<=dim;i++){
            S=S+Sacar(i)+ " , ";
            }
        S=S+"]";
        return S;
    }    
        
    public static void main(String[] args) {
    /* i=0............n-1
    //V=[ 0|0|6|0|1|1|0]
     -----------------------------
       i=1...........n
     V=[ 0|0|6|0|1|1|0]     
        */
        
        
    VectorBitGen X = new VectorBitGen(10,31);
        for (int i = 1; i <= 10; i++) {
                X.Insertar(X.maxval,i);
        }
            
                System.out.println(X);
                X.Insertar(10, 10);
                X.Insertar(100, 6);
                System.out.println(X);
                X.Insertar(1000, 1);
                X.Insertar(1000000, 9);
                System.out.println(X);

	}   
}
