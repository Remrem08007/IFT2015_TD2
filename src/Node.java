import java.util.Arrays;

public class Node {

    private int value;
    private Node next = null;

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }

    public void addValue(int value) {
        if (this.next != null) {
            this.next.addValue(value);
        } else
            this.next = new Node(value);

    }

    public void addNode(Node next) {
        if (this.next != null)
            this.next.addNode(next);
        else
            this.next = next;
    }

    public void removeLast() {
        if (this.next == null)
            System.out.println("You can't remove any item," +
                    " since your list only contains one node");
        if (this.next.next != null)
            this.next.removeLast();
        else
            this.next = null;
    }

    public void removeValue(int value) {
        Node n = this;
        if (this.value == value) {
            while (n.next != null) {
                n.value = n.next.value;
                n = this.next;
            }
            n.next = null;
        } else {
            this.next.removeValue(value);
        }
    }

    public int length_iteratif() {
        int length = 1;
        Node n = this;
        while (n.next != null) {
            n = n.next;
            length++;
        }
        return length;
    }

    public int length_recurssion() {
        if (this.next == null)
            return 1;
        return 1 + this.next.length_recurssion();
    }

    public int returnNlast(int nLast) {
        int length = length_recurssion();
        int positionToReturn = length - nLast + 1;
        Node n = this;
        for (int i = 1; i < positionToReturn; i++) {
            n = this.next;
        }
        return n.value;
    }

    public void addValue_ordered(int value) {
        Node n = this;
        n.insertSort();
        if(value < n.value){
            int temp = n.value;
            n.value = value;
            Node next = new Node(temp, n.next);
            n.next = next;
        } else n.next.addValue_ordered(value);
    }

    public void swap(Node n1, Node n2) {
        int temp = n1.value;
        n1.value = n2.value;
        n2.value = temp;
    }

    public void insertSort() {
        Node n = this;
        int i = 0;
        while (i < this.length_iteratif()) {
            for (int temp = i; temp >= 0; temp--) {
                for (int temp2 = temp; temp2 > 0; temp2--) {
                    if (n.next != null)
                        n = n.next;
                }
                if (n.next != null && n.value > n.next.value) {
                    swap(n, n.next);
                }
                n = this;
            }
            i++;
        }

    }

    public static void main(String[] args) {
        Node linkedList = new Node(5);
        Node n = linkedList;
        int[] list = new int[100];
        linkedList.addValue(1000000);
        System.out.println(linkedList.returnNlast(2));
        linkedList.removeLast();
        linkedList.addValue(33);
        for (int i = 50; i > 0; i--) {
            linkedList.addValue(30 + i);
        }
        linkedList.addValue_ordered(31);

        for (int i = 0; i < linkedList.length_iteratif(); i++) {
            list[i] = n.value;
            if (n.next != null)
                n = n.next;
        }
        System.out.println(Arrays.toString(list));
    }

}
