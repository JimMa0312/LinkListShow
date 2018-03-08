package com.xt.linklistshow.mode;

import java.util.ArrayList;

public class GraphMaterix<E> {
    int GType; //图的类型（0:无向图， 1：有向图）
    int VertexNum;//顶点数
    int EdgeNum; //边数
    ArrayList<ArrayList<E>> Vertex;

    public GraphMaterix() {
        this(0);
    }

    public GraphMaterix(int GType) {
        this.GType = GType;
        VertexNum=0;
        EdgeNum=0;
        Vertex=new ArrayList<>();
    }
    
    public void addVertex(E e){
        if (findVertex(e)==null){
            ArrayList<E> vertexs=new ArrayList<E>();
            vertexs.add(e);
            VertexNum++;
            Vertex.add(vertexs);
        }
    }

    public void deleteVertex(E e){
        ArrayList<E> vertexs=null;
        if ((vertexs=findVertex(e))!=null){
            Vertex.remove(vertexs);
            VertexNum--;
        }
        for (int i=0;i<Vertex.size();i++){
            Vertex.get(i).remove(e);
            EdgeNum--;
        }
    }

    public void addEdge(E fromE,E toE){
        ArrayList<E> vertexs=null;
        if ((vertexs=findVertex(fromE))!=null){
            vertexs.add(toE);
            EdgeNum++;
        }
    }

    public void deleteEdge(E fromE, E toE){
        ArrayList<E> vertexs=null;
        if ((vertexs=findVertex(fromE))!=null){
            vertexs.remove(toE);
            EdgeNum--;
        }
    }
    
    public ArrayList<E> findVertex(E e){
        for (ArrayList<E> arr :
                Vertex) {
            if (e == arr.get(0)) {
                return arr;
            }
        }
        return null;
    }

    public int findEdge(E e, ArrayList<E> vertex){
        return vertex.indexOf(e);
    }

    public ArrayList<ArrayList<E>> getVertex() {
        return Vertex;
    }

    public void setVertex(ArrayList<ArrayList<E>> vertex) {
        Vertex = vertex;
    }

    public int getVertexNum() {
        return VertexNum;
    }

    public void setVertexNum(int vertexNum) {
        VertexNum = vertexNum;
    }

    public int getEdgeNum() {
        return EdgeNum;
    }

    public void setEdgeNum(int edgeNum) {
        EdgeNum = edgeNum;
    }
}
