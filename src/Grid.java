public class grid {
    public class Grid {

        private Cell[] grid = new Cell[12];

        public class Cell {

            private Cell up = null;
            private Cell down = null;
            private Cell left = null;
            private Cell right = null;

            private int value;

            public Cell(Cell up, Cell down, Cell left, Cell right, Integer value) {
                
                this.up = up;
                this.down = down;
                this.left = left;
                this.right = right;

                this.value = value;
            }
        }

        public Grid() {
            // TODO
        }

        public boolean move(Cell box) {
            // TODO
            return true;
        }

        public boolean check_complete() {
            // TODO
            return true;
        }

        public void solve_game() {
            // TODO
        }
    }
}
