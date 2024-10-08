package no.oslomet.cs.algdat;

import java.util.Objects;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringJoiner;

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
            antall++;
            return true;
        }

        Node<T> node = rot;

        while (true) {
            if (comp.compare(verdi, node.verdi) < 0) {
                if (node.venstre == null) {
                    node.venstre = new Node<>(verdi,null, null, node);
                    break;
                }

                node = node.venstre;
            } else {
                if (node.høyre == null) {
                    node.høyre = new Node<>(verdi, null, null, node);
                    break;
                }

                node = node.høyre;
            }
        }

        antall++;
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
            } else if (comparisonValue > 0) {
                node = node.høyre;
            } else {
                numberOfValue++;
                node = node.høyre;
            }
        }

        return numberOfValue;
    }

    // Oppgave 3
    // returns the first node in post order where input node is root
    private Node<T> førstePostorden(Node<T> p) {
        if(p == null) return null;
        if (p.høyre == null && p.venstre == null) return p;
        Node<T> node = førstePostorden(p.venstre);
        if (node == null) {
            node = førstePostorden(p.venstre);
        }

        return node;
    }

    // Returns the next node in post order
    private Node<T> nestePostorden(Node<T> p) {
        if(p == null) return null;
        if (p.høyre == null && p.venstre == null) førstePostorden(p.forelder);
        Node<T> node = førstePostorden(p.venstre);
        if (node == null) {
            node = førstePostorden(p.venstre);
        }

        return node;
    }

    // Oppgave 4
    public void postOrden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException();
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
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException();
    }

    public void nullstill() {
        throw new UnsupportedOperationException();
    }
}