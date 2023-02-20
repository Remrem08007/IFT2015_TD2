import java.util.*;

public class Grid {

    private Cell[] grid;

    private int[] values = { 1, 1, 1, 1, 5, 5, 10, 10, 10, 10, 25, -1 };

    public class Cell {

        private Cell up = null;
        private Cell down = null;
        private Cell left = null;
        private Cell right = null;

        private int value;

        public Cell() {

            this.up = null;
            this.down = null;
            this.left = null;
            this.right = null;

            this.value = 0;
        }
    }

    public Grid() {
        this.grid = new Cell[12];
        int index, temp;
        // Randomisation des valeurs de la grille de jeu
        Random random = new Random();
        for (int i = this.values.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = this.values[index];
            this.values[index] = this.values[i];
            this.values[i] = temp;
        }

        // Creation des cellules de la grille
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new Cell();
            grid[i].value = this.values[i];
        }

        // Assignement des valeurs et des pointeurs
        grid[0].right = grid[1];
        grid[0].down = grid[4];
        for (int i = 1; i <= 2; i++) {
            grid[i].right = grid[i + 1];
            grid[i].down = grid[i + 4];
            grid[i].left = grid[i - 1];
        }
        grid[3].left = grid[2];
        grid[3].down = grid[7];
        grid[4].up = grid[0];
        grid[4].down = grid[8];
        grid[4].right = grid[5];
        for (int i = 5; i <= 6; i++) {
            grid[i].right = grid[i + 1];
            grid[i].down = grid[i + 4];
            grid[i].left = grid[i - 1];
            grid[i].up = grid[i - 4];
        }
        grid[7].up = grid[3];
        grid[7].down = grid[11];
        grid[7].left = grid[6];
        grid[8].up = grid[4];
        grid[8].right = grid[9];
        for (int i = 9; i <= 10; i++) {
            grid[i].right = grid[i + 1];
            grid[i].left = grid[i - 1];
            grid[i].up = grid[i - 4];
        }
        grid[11].up = grid[7];
        grid[11].left = grid[10];

        this.print();
    }

    // Fonction permettant d'imprimer la grille
    public void print() {
        int i;

        System.out.print("[");
        for (i = 0; i < 3; i++) {
            if (this.grid[i].value == -1)
                System.out.printf("%4s" + ", ", "X");
            else
                System.out.printf("%4d" + ", ", this.grid[i].value);
        }
        if (this.grid[i].value == -1)
            System.out.printf("%4s" + "]\n", "X");
        else
            System.out.printf("%4d" + "]\n", this.grid[i].value);

        System.out.print("[");
        for (i = 4; i < 7; i++) {
            if (this.grid[i].value == -1)
                System.out.printf("%4s" + ", ", "X");
            else
                System.out.printf("%4d" + ", ", this.grid[i].value);

        }
        if (this.grid[i].value == -1)
            System.out.printf("%4s" + "]\n", "X");
        else
            System.out.printf("%4d" + "]\n", this.grid[i].value);

        System.out.print("[");
        for (i = 8; i < 11; i++) {
            if (this.grid[i].value == -1)
                System.out.printf("%4s" + ", ", "X");
            else
                System.out.printf("%4d" + ", ", this.grid[i].value);
        }
        if (this.grid[i].value == -1)
            System.out.printf("%4s" + "]\n", "X");
        else
            System.out.printf("%4d" + "]\n", this.grid[i].value);
        System.out.println();
    }

    // Permet de se deplacer en fonction de la cellule selectionee
    public boolean move(Cell box) {
        if (box.up != null && box.up.value == -1) {
            swap(box, box.up);
            return true;
        } else if (box.down != null && box.down.value == -1) {
            swap(box, box.down);
            return true;
        } else if (box.left != null && box.left.value == -1) {
            swap(box, box.left);
            return true;
        } else if (box.right != null && box.right.value == -1) {
            swap(box, box.right);
            return true;
        } else
            System.out.println("That cell isn't next to the X, please select" +
                    " another one.");
        return false;

    }

    // Permet d'echanger la valeur de deux cellules
    public static void swap(Cell cell1, Cell cell2) {
        int temp = cell2.value;
        cell2.value = cell1.value;
        cell1.value = temp;
    }

    public boolean check_complete() {
        if (this.grid[0].value != this.grid[8].value)
            return false;
        if (this.grid[1].value != this.grid[9].value)
            return false;
        if (this.grid[2].value != this.grid[10].value)
            return false;
        if (this.grid[3].value != this.grid[11].value)
            return false;
        return true;
    }

    // Permet de trouver l'index d'une valeur donnee dans un array
    public int indexOf(int key) {
        int returnvalue = -1;
        for (int i = 0; i < this.grid.length; ++i) {
            if (key == this.grid[i].value) {
                returnvalue = i;
                break;
            }
        }
        return returnvalue;
    }

    // Permet de trouver la solution de la grille
    public void solve_game() {

        int indexOfX = -1;
        for (int i = 0; i < this.grid.length; ++i) {
            if (-1 == this.grid[i].value) {
                indexOfX = i;
                break;
            }
        }
        if (indexOfX > 7) {
            swap(this.grid[indexOfX], this.grid[indexOfX - 4]);
            indexOfX -= 4;
        }
        if (indexOfX < 4) {
            swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
            indexOfX = +4;
        }

        this.print();

        int goTo = 0;

        int[] row1 = new int[4];
        for (int i = 0; i < 4; i++) {
            row1[i] = this.grid[i].value;
        }
        List<Integer> list = Arrays.stream(row1).boxed().toList();

        int j = 0;

        // Je m'assure en premier que la premiere ligne ne contient pas une
        // combinason impossible de chiffres, comme 1, 1, 1, 5 ou 25, 10 ,1 ,1.
        // Je modifie ensuite le reste de la grille afin de tenter de matcher
        // la premiere ligne. Lorsque cela semble impossible, je melange un peu
        // la grille puis je tente a nouveau.
        while ((Collections.frequency(list, 1) > 2 || Collections.frequency(list, 25) > 0
                || Collections.frequency(list, 10) > 2 || Collections.frequency(list, 5) > 1) && j < 10) {
            if (Collections.frequency(list, 1) > 2) {
                goTo = this.indexOf(1);
                while (indexOfX != goTo + 4) {
                    if (indexOfX > goTo + 4) {
                        swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                        indexOfX -= 1;
                        this.print();
                    } else {
                        swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                        indexOfX += 1;
                        this.print();
                    }
                }
                swap(this.grid[indexOfX], this.grid[goTo]);
                indexOfX = goTo;
                this.print();
                if (indexOfX != 3) {
                    swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                    indexOfX++;
                    this.print();
                } else {
                    swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                    indexOfX--;
                    this.print();
                    swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                    indexOfX--;
                    this.print();
                }
                swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
                indexOfX += 4;
                this.print();
                row1 = new int[4];
                for (int i = 0; i < 4; i++) {
                    row1[i] = this.grid[i].value;
                }
                list = Arrays.stream(row1).boxed().toList();
            }
            if (Collections.frequency(list, 25) > 0) {
                goTo = this.indexOf(25);
                while (indexOfX != goTo + 4) {
                    if (indexOfX > goTo + 4) {
                        swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                        indexOfX -= 1;
                        this.print();
                    } else {
                        swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                        indexOfX += 1;
                        this.print();
                    }
                }
                swap(this.grid[indexOfX], this.grid[goTo]);
                indexOfX = goTo;
                this.print();
                if (indexOfX != 3) {
                    swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                    indexOfX++;
                    this.print();
                } else {
                    swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                    indexOfX--;
                    this.print();
                    swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                    indexOfX--;
                    this.print();
                }
                swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
                indexOfX += 4;
                this.print();
                row1 = new int[4];
                for (int i = 0; i < 4; i++) {
                    row1[i] = this.grid[i].value;
                }
                list = Arrays.stream(row1).boxed().toList();
            }
            if (Collections.frequency(list, 10) > 2) {
                goTo = this.indexOf(10);
                while (indexOfX != goTo + 4) {
                    if (indexOfX > goTo + 4) {
                        swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                        indexOfX -= 1;
                        this.print();
                    } else {
                        swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                        indexOfX += 1;
                        this.print();
                    }
                }
                swap(this.grid[indexOfX], this.grid[goTo]);
                indexOfX = goTo;
                this.print();
                if (indexOfX != 3) {
                    swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                    indexOfX++;
                    this.print();
                } else {
                    swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                    indexOfX--;
                    this.print();
                    swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                    indexOfX--;
                    this.print();
                }
                swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
                indexOfX += 4;
                this.print();
                row1 = new int[4];
                for (int i = 0; i < 4; i++) {
                    row1[i] = this.grid[i].value;
                }
                list = Arrays.stream(row1).boxed().toList();
            }
            if (Collections.frequency(list, 5) > 1) {
                goTo = this.indexOf(5);
                while (indexOfX != goTo + 4) {
                    if (indexOfX > goTo + 4) {
                        swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                        indexOfX -= 1;
                        this.print();
                    } else {
                        swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                        indexOfX += 1;
                        this.print();
                    }
                }
                swap(this.grid[indexOfX], this.grid[goTo]);
                indexOfX = goTo;
                this.print();
                if (indexOfX != 3) {
                    swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                    indexOfX++;
                    this.print();
                } else {
                    swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                    indexOfX--;
                    this.print();
                    swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                    indexOfX--;
                    this.print();
                }
                swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
                indexOfX += 4;
                this.print();
                row1 = new int[4];
                for (int i = 0; i < 4; i++) {
                    row1[i] = this.grid[i].value;
                }
                list = Arrays.stream(row1).boxed().toList();
            }
            j++;
        }

        int i = 0;

        while (this.grid[0].value != this.grid[8].value && i < 8) {
            while (indexOfX != 7) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                indexOfX++;
                this.print();
            }
            swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
            indexOfX += 4;
            this.print();
            while (indexOfX != 8) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                indexOfX--;
                this.print();
            }
            swap(this.grid[indexOfX], this.grid[indexOfX - 4]);
            indexOfX -= 4;
            this.print();
            i++;
        }

        i = 0;

        while (this.grid[1].value != this.grid[9].value && i < 6) {
            while (indexOfX != 7) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                indexOfX++;
                this.print();
            }
            swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
            indexOfX += 4;
            this.print();
            while (indexOfX != 9) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                indexOfX--;
                this.print();
            }
            swap(this.grid[indexOfX], this.grid[indexOfX - 4]);
            indexOfX -= 4;
            this.print();
            i++;
        }

        i = 0;

        while (this.grid[2].value != this.grid[10].value && i < 4) {
            while (indexOfX != 7) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                indexOfX++;
                this.print();
            }
            swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
            indexOfX += 4;
            this.print();
            while (indexOfX != 10) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                indexOfX--;
                this.print();
            }
            swap(this.grid[indexOfX], this.grid[indexOfX - 4]);
            indexOfX -= 4;
            this.print();
            i++;
        }
        if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
            if (indexOfX > 4) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                indexOfX--;
                this.print();
            }
            if (indexOfX > 4) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                indexOfX--;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
                indexOfX += 4;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                indexOfX++;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                indexOfX += 1;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 4]);
                indexOfX -= 4;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 4]);
                indexOfX -= 4;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                indexOfX += 1;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
                indexOfX += 4;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                indexOfX -= 1;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                indexOfX -= 1;
                this.print();
            }
            Random rand = new Random();
            int randomNum = 1 + rand.nextInt((5 - 1) + 1);
            if ((this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value)
                    && randomNum == 4) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 1]);
                indexOfX -= 1;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX - 4]);
                indexOfX -= 4;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 1]);
                indexOfX += 1;
                this.print();
            }
            if (this.grid[3].value != this.grid[11].value || this.grid[0].value != this.grid[8].value
                    || this.grid[2].value != this.grid[10].value || this.grid[1].value != this.grid[9].value) {
                swap(this.grid[indexOfX], this.grid[indexOfX + 4]);
                indexOfX += 4;
                this.print();
                this.solve_game();
            }
        }
    }

    public static void main(String[] args) {
        Grid game = new Grid();
        String in = "";

        while (in != "q") {
            in = System.console().readLine("Select the action you want to do" +
                    " (m = move, s = solve): ");
            if (in.equals("m")) {
                in = System.console().readLine("Select the cell you want to move." +
                        " Keep in mind that the cells are numbered like the ones" +
                        " on a dialing pad: ");
                System.out.println("You entered: " + in + "\n");
                game.move(game.grid[Integer.parseInt(in) - 1]);
                game.print();
                boolean check = game.check_complete();
                if (check) {
                    System.out.print("You have solved the game!");
                    in = "q";
                }

            } else if (in.equals("s")) {
                game.solve_game();
                game.print();
                System.out.println("You have solved the game!");
                in = "q";
            } else
                System.out.println("This action isn't allowed. Try move or solve.");
        }
    }
}
