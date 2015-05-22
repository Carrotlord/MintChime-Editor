package builtin.game;

import builtin.BuiltinSub;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.MintObject;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;
import gui.Subprogram;

/**
 * New style objects.
 * New style Mint constructors do not rely on the interpreter to evaluate
 * methods. They return a generic Mint Object.
 * @author Oliver Chu
 */
public class TextBasedGame extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        MintObject retn = new MintObject();
        SmartList<Pointer> noArgs = new SmartList<Pointer>();
        Subprogram boardGame = new Subprogram("boardGame", noArgs,
                                              new BoardGame());
        retn.put("BoardGame", Heap.allocateSub(boardGame));
        retn.put("CardGame", Constants.MINT_NULL);
        retn.put("EconomyGame", Constants.MINT_NULL);
        retn.put("StrategyGame", Constants.MINT_NULL);
        retn.put("RPG", Constants.MINT_NULL);
        return Heap.allocateObject(retn);
    }
    
    public class BoardGame extends BuiltinSub {
        private char[][] gameBoard = null;
        
        public BoardGame() {
            gameBoard = new char[8][8];
            for (int k = 0; k < 8; ++k) {
                for (int j = 0; j < 8; ++j) {
                    gameBoard[k][j] = ' ';
                }
            }
        }

        @Override
        public Pointer apply(SmartList<Pointer> args) {
            MintObject retn = new MintObject();
            SmartList<Pointer> threeArgs = new SmartList<Pointer>();
            threeArgs.add(Heap.allocateName("x"));
            threeArgs.add(Heap.allocateName("y"));
            threeArgs.add(Heap.allocateName("contents"));
            Subprogram changeTile = new Subprogram("changeTile", threeArgs,
                                    new ChangeTile());
            Subprogram display = new Subprogram("viewBoard",
                       new SmartList<Pointer>(), new BoardDisplay());
            retn.put("changeTile", Heap.allocateSub(changeTile));
            retn.put("viewBoard", Heap.allocateSub(display));
            return Heap.allocateObject(retn);
        }
        
        public class ChangeTile extends BuiltinSub {
            @Override
            public Pointer apply(SmartList<Pointer> args) {
                Pointer p = args.get(2);
                int x = args.get(0).value;
                int y = args.get(1).value;
                String s = PointerTools.dereferenceString(p);
                char changed = s.charAt(0);
                gameBoard[y][x] = changed;
                return p;
            }
        }
        
        public class BoardDisplay extends BuiltinSub {
            @Override
            public Pointer apply(SmartList<Pointer> args) {
                System.out.println(" -------- ");
                for (int j = 0; j < 8; ++j) {
                    System.out.print("|");
                    for (int i = 0; i < 8; ++i) {
                        System.out.print(gameBoard[j][i]);
                    }
                    System.out.println("|");
                }
                System.out.println(" -------- ");
                return Constants.MINT_NULL;
            }
        }
    }
}
