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
    @SuppressWarnings("unchecked")
    public void postOrden(Oppgave<? super T> oppgave) {

        Node node = førstePostorden(rot);
        while (node != null) {
            oppgave.utførOppgave((T) node.verdi);
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
            else if (comp.compare(value,node.verdi) == 0) return node;
        }
        return null;
    }

    // Removes the input node from the binary tree
    public void removeNode(Node<T> node) {

        // If Node doesnt have children
        if (node.venstre == null && node.høyre == null) {
            if (node.forelder == null) rot = null;
            else if (node.forelder.venstre == node) {
                node.forelder.venstre = null;
            } else {
                node.forelder.høyre = null;
            }
            node.forelder = null;
            antall--; endringer++;

            // If node have one child
        } else if (node.venstre == null) {
            if (node.forelder == null){
                rot = node.høyre;
                rot.forelder = null;
            } else {
                if (node.forelder.venstre == node) {
                    node.forelder.venstre = node.høyre;
                } else {
                    node.forelder.høyre = node.høyre;
                }
                node.høyre.forelder = node.forelder;
            }
            node.forelder = null;
            antall--; endringer++;

        } else if (node.høyre == null){
            if (node.forelder == null) {
                rot = node.venstre;
                rot.forelder = null;
            } else {
                if (node.forelder.venstre == node) {
                    node.forelder.venstre = node.venstre;
                } else {
                    node.forelder.høyre = node.venstre;
                }
                node.venstre.forelder = node.forelder;
            }
            node.forelder = null;
            antall--; endringer++;

            // If Node has two children, copy the value of the leftmost node of the right child and remove that node
        } else {
            Node<T> bottomNode = node.høyre;
            while(bottomNode.venstre != null) bottomNode = bottomNode.venstre;
            node.verdi = bottomNode.verdi;
            removeNode(bottomNode);
        }
    }

    // Removes All nodes with a certain value. Returns number of nodes removed.
    public int fjernAlle(T verdi) {
        boolean continueRemoving = true;
        int numberOfRemoved = 0;

        while (continueRemoving) {
            if (fjern(verdi)) {
                numberOfRemoved++;
            } else continueRemoving = false;
        }
        return numberOfRemoved;
    }

    public void nullstill() {
        while (rot != null) removeNode(rot);
    }
}
