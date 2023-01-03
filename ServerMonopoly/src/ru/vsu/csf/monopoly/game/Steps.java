package ru.vsu.csf.monopoly.game;

public enum Steps {
    CHOOSE_COMMAND("choose command"),
    CHOOSE_COMPANY_COMMAND("choose company command"),
    DRAW_STRING("draw string"),
    DRAW_DICE("draw dice"),
    CHOOSE_CASINO_COMMAND("choose casino command"),
    CHOOSE_RIALTO_COMMAND("choose rialto command"),
    NOTHING("nothing");

    private String value;

    Steps(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }

    public static Steps getStep(String str){
        switch (str){
            case("choose command"):
                return CHOOSE_COMMAND;
            case("choose company command"):
                return CHOOSE_COMPANY_COMMAND;
            case("draw string"):
                return DRAW_STRING;
            case("draw dice"):
                return DRAW_DICE;
            case("choose casino command"):
                return CHOOSE_CASINO_COMMAND;
            case("choose rialto command"):
                return CHOOSE_RIALTO_COMMAND;
            case ("nothing"):
                return NOTHING;
            default:
                return null;
        }
    }
}