public class File {
    public int[] elements;
    public int start;
    public int end;

    public File() {
        this.elements = new int[100];
        this.start = 0;
        this.end = 0;
        return;
    }

    public void push(int element) {
        if (this.length() == 99)
            System.out.println("The queue is full," +
                    " remove an element before pushing a new one.");
        else {
            this.elements[this.end] = element;
            this.end = (this.end + 1) % 100;
        }
    }

    public int pop() {
        if (this.start == this.end)
            System.out.println("The queue is empty," +
                    " push some elements before using pop()");
        else {
            int temp = this.elements[this.start];
            this.start = (this.start + 1) % 100;
            System.out.println(temp);
            return temp;
        }
        return -1;
    }

    public int length() {
        return (this.end - this.start + 100) % 100;
    }

    public void print() {
        int s = this.start;
        System.out.print("(");
        while (s + 1 != this.end) {
            System.out.print(this.elements[s] + ", ");
            s = (s + 1) % 100;
        }
        System.out.println(this.elements[s] + ")");
    }

    public boolean search(int value) {
        int s = this.start;
        while (s != this.end) {
            if (this.elements[s] == value)
                return true;
            s = (s + 1) % 100;
        }
        return false;
    }

    public void remove(int value) {
        int s = this.start;
        while (s != this.end) {
            if (this.elements[s] == value) {
                int i = s + 1;
                while (i != this.end) {
                    this.elements[i - 1] = this.elements[i];
                    i = (i + 1) % 100;
                }
                this.end -= 1;
                break;
            }
            s = (s + 1) % 100;
        }
    }

    public static void main(String[] args) {
        File file = new File();
        file.push(5);
        file.push(1928193);
        file.remove(5);
        file.push(5);
        file.push(10);
        System.out.println(file.length());
        System.out.println(file.search(11));
        file.pop();
        file.print();
        for (int i = 1; i <= 100; i++) {
            file.push(5);
        }
        System.out.println(file.length());
        for (int i = 1; i <= 100; i++) {
            file.pop();
        }
    }

}
