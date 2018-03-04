package pl.krzysztofbaranski;

public class Saper {

    private boolean showLogs;

    private static String[][] table;
    private int rows;
    private int columns;

    Saper(int numberOfRows, int numberOfColumns, boolean debugMode){

        showLogs = debugMode;

        table = new String[numberOfRows][numberOfColumns];

        rows = numberOfRows;
        columns = numberOfColumns;

        fillTableWithBombs();

        printTable();

    }

    private void printTable() {

        String tableView = "";

        for (int i = 0; i < rows; i++) {

            tableView += "[";

            for (int j = 0; j < columns; j++) {

                if(j == columns - 1){

                    tableView += table[i][j];

                }else{

                    tableView += table[i][j] + " ";

                }

            }

            tableView += "]\n";

        }

        System.out.println(tableView);

    }

    public void evaluate(){

        //Go through rows
        for (int i = 0; i < rows; i++) {

            //Go through columns
            for (int j = 0; j < columns; j++) {

                if( checkBomb( table[i][j] ) ){ //If there is a bomb in position ixj then evaluate number around that bomb

                    if(showLogs) {
                        System.out.printf("# Na pozycji %d %d jest bomba\n", i, j);
                    }

                    // Go through elements around position of bomnb
                    // t t t
                    // t b t
                    // t t t
                    // t - element to check if: bomb | out of bounds | value to add up
                    // b - bomb
                    for (int k = -1; k <= 1; k++) {

                        for (int l = -1; l <= 1; l++) {

                            // Check if element t is out of the bounds
                            if( !checkOutOfBounds(j, l, false) && !checkOutOfBounds(i, k, true) ){

                                // If position is inside of the bounds - then check if at the position of [i + k][j + l] is bomb - if not - add up ( +1 ) information about souranded bomb
                                if( !checkBomb( table[i + k][j + l] ) ) {

                                    // item t is empty - set to value 0
                                    if( table[i + k][j + l].equals(" ") ){

                                        table[i + k][j + l] = "0";

                                    }

                                    // add up ( +1 )
                                    int newVal = Integer.parseInt( table[i + k][j + l]) + 1;
                                    table[i + k][j + l] = "" + newVal;

                                    if(showLogs) {
                                        System.out.printf("#### W zakresie %d %d Wartość %s\n", i + k, j + l, table[i + k][j + l]);
                                    }

                                }

                            }

                        }

                    }

                }else{ //At position ixj there is no bomb - set item[i][j] to 0 if empty

                    if(showLogs) {
                        System.out.printf("Na pozycji %d %d nie ma bomby\n", i, j);
                    }

                    if( table[i][j].equals(" ") ) {

                        table[i][j] = "0";

                    }

                }

            }

        }

        printTable();

    }

    //Check if item t is out of the bounds
    // rc - true - < 0, rows > - check inside row
    // rc - false - < 0, columns > - check inside column
    private boolean checkOutOfBounds(int v, int p, boolean rc){

        int max;
        int valueToCheck = v + p;

        if(rc){

            max = rows;

        }else{

            max = columns;
        }

        return valueToCheck < 0 || valueToCheck >= max;

    }

    //Return information about the bomb
    private boolean checkBomb(String item) {

        return item.equals("x");

    }

    //Filling table with random number of bombs at random positions - recursion
    private void fillTableWithBombs() {

        int numberOfBombsInRow;
        int bombPosition[];

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                table[i][j] = " ";

            }

            numberOfBombsInRow = Helpers.getRandomNumber(0, columns);
            bombPosition = new int[numberOfBombsInRow];

            for (int p = 0; p < numberOfBombsInRow; p++) {

                bombPosition[p] = checkIfRandomPositionOfBombExists(p, bombPosition);

                table[i][bombPosition[p]] = "x";

            }

        }

    }

    //Check if value from arrayOfBombs[position] exists in row of arrayOfBombs - recursion
    private int checkIfRandomPositionOfBombExists(int position, int[] bP) {

        int randomPosition = Helpers.getRandomNumber(0, columns);
        int numberOfElements = bP.length;

        return (position == 0) ? randomPosition : checkIfRandomPositionOfBombExists(randomPosition, bP);


    }

}
