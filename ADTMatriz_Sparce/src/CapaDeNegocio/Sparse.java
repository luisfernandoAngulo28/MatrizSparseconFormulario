/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDeNegocio;

/**
 *
 * @author ferna
 */

public class Sparse {
    float VD[];
    int VFC[];
    int dimension,numeroFila,numeroColumnas,elementoPredominante;
    public Sparse(int nf,int nc,int ele){
        VFC=new int[10];
        VD=new float[10];
        numeroColumnas=nc;
        numeroFila=nf;
        dimension=-1;
        elementoPredominante=ele;
    }
    public void setDato(int f,int c,float dato){
        if ((f>0)&&(c>0)&&(f<=numeroFila)&&(c<=numeroColumnas)){
            int fc=(f-1)*numeroColumnas+c;
            int pos=buscar(fc);// si no encuentra devuelve -1 y si no la posicion 
            if(pos!=-1){//si queremos agregar un valor repetido
                if(dato==elementoPredominante){  //si es igual al elementoprediminate
                    dimension--;
                    for (int i = pos; i < dimension; i++) {
                        VFC[i]=VFC[i+1];
                        VD[i]=VD[i+1];
                    }
                }else{//si no es igual al elementoprediminate
                    VD[pos]=dato;
                }
            }else{  //si el vd esta vacio
                if(dato!=elementoPredominante){
                    Redimencionar();
                    dimension++;
                    VD[dimension]=dato;
                    VFC[dimension]=(f-1)*numeroColumnas+c;
                }
            }
        }else{
            System.out.println("Error: la fila o columna esta fuera de rango");
        }
    }                
    
    private int buscar(int fc){
        int i=0;
        while ((i<=dimension)&&(VFC[i]!=fc)){
            i++;
        }
            if (i>dimension){
                return -1;
            }else
                return i;       
    }
    private void Redimencionar(){
        if((dimension%9==0)&&(dimension!=0)){
            float vaux[]=new float[dimension+10];
            System.arraycopy(VFC, 0, VD, 0, dimension+1);
            int vfc2[]=new int [dimension+10];
            System.arraycopy(VFC, 0, vfc2,0, dimension+1);
        }
    }
    
    public float getDato(int f,int c){
        float valor=elementoPredominante;
        if((f>0)&&(c>0)&&(f<=numeroFila)&&(c<=numeroColumnas)){
            int pos=buscar((f-1)*numeroColumnas+c);
            if(pos!=-1){
                valor=VD[pos];
            }               
        }else
            System.out.println("Error: Fila o columna fuera de rango0o");
        return valor;
    }
    
    public String toString(int fila){
        String s="";
        if ((fila>0)&&(fila<=numeroFila)){
            for (int i = 0; i < numeroColumnas; i++) {
                s=s+" "+getDato(fila, i+1);
            }
        }
        return s;
    }
    
    public String toString(){
        String S="";
        for (int i = 1; i <=numeroColumnas; i++) {
            for (int j = 1; j <= numeroFila; j++) {
                S=S+getDato(i, j)+" ";
            }
            S=S+"\n";
        }
        return S;
    }
    
    public static void main(String[] args) {
        Sparse a=new Sparse(5, 5, 0);
        a.setDato(1, 3, 8);
        a.setDato(1, 4, 5);
        a.setDato(2, 1, 5);
        a.setDato(2, 2, 8);
        a.setDato(2, 3, 4);
        a.setDato(2, 4, 6);
        a.setDato(3, 1, 1);
        a.setDato(3, 2, 3);
        a.setDato(3, 3, 6);
        System.out.println(a.toString());
    /*    for (int i = 0; i < 3; i++) {
            System.out.println(a.toString(i+1));
        }*/
    }
}

