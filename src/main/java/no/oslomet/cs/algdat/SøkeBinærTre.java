package no.oslomet.cs.algdat;

import java.util.*;

public class SøkeBinærTre<T>  implements Beholder<T> {

    // En del kode er ferdig implementert, hopp til linje 91 for Oppgave 1

    private static final class Node<T> { // En indre nodeklasse
        private T verdi; // Nodens verdi
        private Node<T> venstre, høyre, forelder; // barn og forelder

        // Konstruktører
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> f) {
            this.verdi = verdi;
            venstre = v; høyre = h; forelder = f;
        }
        private Node(T verdi, Node<T> f) {
            this(verdi, null, null, f);
        }

        @Override
        public String toString() {return verdi.toString();}
    } // class Node

    private final class SBTIterator implements Iterator<T> {
        Node<T> neste;
        public SBTIterator() {
            neste = førstePostorden(rot);
        }

        public boolean hasNext() {
            return (neste != null);
        }

        public T next() {
            Node<T> denne = neste;
            neste = nestePostorden(denne);
            return denne.verdi;
        }
    }

    public Iterator<T> iterator() {
        return new SBTIterator();
    }

    private Node<T> rot;
    private int antall;
    private int endringer;

    private final Comparator<? super T> comp;

    public SøkeBinærTre(Comparator<? super T> c) {
        rot = null; antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }
        return false;
    }

    public int antall() { return antall; }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot);
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() { return antall == 0; }

    // Oppgave 1
    // Adds the value to the binary search tree
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi);
        if (rot == null) {
            rot = new Node<>(verdi, null);
            antall++; endringer++;
            return true;
        }

        Node<T> node = rot;

        while (true) {
            if (comp.compare(verdi, node.verdi) < 0) {
                if (node.venstre == null) {
                    node.venstre = new Node<>(verdi, node);
                    break;
                }

                node = node.venstre;
            } else {
                if (node.høyre == null) {
                    node.høyre = new Node<>(verdi, node);
                    break;
                }

                node = node.høyre;
            }
        }

        antall++; endringer++;
        return true;
    }

    // Oppgave 2
    // Returns the number of a certain value in the binary search tree
    public int antall(T verdi){
        if (verdi == null || rot == null) return 0;

        int numberOfValue = 0;
        Node<T> node = rot;

        while (node != null) {
            int comparisonValue = comp.compare(verdi, node.verdi);
            if (comparisonValue < 0) {
                node = node.venstre;
            } else {
                if (comparisonValue == 0) numberOfValue++;
                node = node.høyre;
            }
        }

        return numberOfValue;
    }

    // Oppgave 3
    // Returns the first node in post order where input node is root with recursion
    private Node<T> førstePostorden(Node<T> p) {
        if(p == null) return null;
        if (p.venstre != null) return førstePostorden(p.venstre);
        if (p.høyre != null) return førstePostorden(p.høyre);
        return p;
    }

    // Same code with loops instead of recursion:
/*
    private Node<T> førstePostorden(Node<T> p) {
        if(p == null) return null;
        while (true) {
            if (p.venstre != null)  p = p.venstre;
            else if (p.høyre != null)  p = p.høyre;
            else return p;
        }
    }
*/

    // Returns the next node in post order with recursion
    private Node<T> nestePostorden(Node<T> p) {
        if (p.forelder == null) return null;
        if (p.equals(p.forelder.høyre)) return p.forelder;
        if (p.forelder.høyre == null) return p.forelder;
        return førstePostorden(p.forelder.høyre);
    }

    // Oppgave 4
    // Do the input task in post order on the binary tree. Using methods from
    public void postOrden(Oppgave<? super T> oppgave) {

        Node<T> node = førstePostorden(rot);
        while (node != null) {
            oppgave.utførOppgave(node.verdi);
            node = nestePostorden(node);
        }
    }

    // Traverses the binary tree recursively from root node, doing the task post order
    public void postOrdenRekursiv(Oppgave<? super T> oppgave) {
        postOrdenRekursiv(rot, oppgave); // Ferdig implementert
    }

    // Traverses the binary tree recursively from input node, doing the task post order
    private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null) return;

        postOrdenRekursiv(p.venstre, oppgave);
        postOrdenRekursiv(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    // Oppgave 5
    // Removes the first node with a certain value, makes sure pointers is correct after.
    public boolean fjern(T verdi) {
        Node<T> node = findNode(verdi);
        if (node == null) return false;

        removeNode(node);
        return true;
    }

    // Finds the first node with a certain value based on binarysearchtree  and returns it.
    // Starts at root
    private Node<T> findNode(T value) {
        return findNode(rot, value);
    }

    // Finds the first node with a certain value based on binarysearchtree  and returns it.
    // Starts at the node that is input in to it.
    private Node<T> findNode(Node<T> node,T value) {

        while (node != null) {
            int compValue = comp.compare(value, node.verdi);
            if (compValue < 0 ) node = node.venstre;
            else if(compValue > 0) node = node.høyre;
            else return node;
        }
        return null;
    }

    // Removes the input node from the binary tree
    private void removeNode(Node<T> node) {

        // If Node doesn't have children
        if (node.venstre == null && node.høyre == null) {
            if (node.forelder == null) rot = null;          // If node is root
            else if (node.forelder.venstre == node) {       // If node is left child
                node.forelder.venstre = null;
            } else {
                node.forelder.høyre = null;
            }
            node.forelder = null;
            antall--; endringer++;

            // If node have one child move the child upwards
        } else if(node.venstre == null || node.høyre == null) {
            Node<T> barn;
            if (node.venstre != null) barn = node.venstre;
            else barn = node.høyre;

            if (node.forelder == null) {                    // If node is root
                rot = barn;
                barn.forelder = null;
            } else {
                if (node.forelder.venstre == node) {
                    node.forelder.venstre = barn;
                } else {
                    node.forelder.høyre = barn;
                }
                barn.forelder = node.forelder;
            }
            node.forelder = null;
            antall--; endringer++;

            // If Node has two children, copy the value of the leftmost node of the right child (next inorden) and
            // remove that node
        } else {
            Node<T> bottomNode = node.høyre;
            while(bottomNode.venstre != null) bottomNode = bottomNode.venstre;
            node.verdi = bottomNode.verdi;
            removeNode(bottomNode);
        }
    }

    // Removes all nodes with a value, but does not start at the start every time removes the bottom up
    public int fjernAlle(T verdi) {
        ArrayDeque<Node<T>> nodes = new ArrayDeque<>();
        int numberOfRemoved = 0;

        Node<T> node = findNode(verdi);

        while (node != null) {
            nodes.addLast(node);
            node = findNode(node.høyre, verdi);
        }

        while (!nodes.isEmpty()){
            removeNode(nodes.removeLast());
            numberOfRemoved++;
        }

        return numberOfRemoved;
    }

    // Removes all elements from the tree in post order and makes sure pointers are disconnected.
    public void nullstill() {
        removeAllPointers(rot);
        rot = null;
    }

    private void removeAllPointers(Node<T> node) {
        if (node == null) return;

        removeAllPointers(node.venstre);
        removeAllPointers(node.høyre);
        node.forelder = null;
        node.venstre = null;
        node.høyre = null;
        antall--; endringer++;
    }


    // Alternate solution with Queue, is slower than the recursive version
   /* public void nullstill() {
        ArrayDeque<Node<T>> nodes = new ArrayDeque<>();
        addNodes(rot, nodes);

        while (!nodes.isEmpty()) {
            Node<T> nodeI = nodes.removeFirst();
            nodeI.forelder = null;
            nodeI.venstre = null;
            nodeI.høyre = null;
            antall--;
        }

        rot = null;
    }

    private void addNodes(Node<T> node,ArrayDeque<Node<T>> nodes) {
        if (node != null) {
            nodes.addLast(node);
            if (node.venstre != null) addNodes(node.venstre, nodes);
            if (node.høyre != null) addNodes(node.høyre, nodes);
        }
    }*/

}
