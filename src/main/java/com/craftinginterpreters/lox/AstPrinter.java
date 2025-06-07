// This is a file I wrote just to test the ASTs, no role in the compiler

package com.craftinginterpreters.lox;

class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme(),
                expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme(), expr.right);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(expr.accept(this));
            builder.append(" ");
        }
        builder.append(")");

        return builder.toString();
    }

    public String visitTernaryExpr(Expr.Ternary expr) {
        return parenthesize("?:", expr.condition, expr.thenBranch, expr.elseBranch);
    }

    public static void main(String[] args) {
        Expr expression = new Expr.Ternary(
                new Expr.Binary(
                        new Expr.Literal(1),
                        new Token(TokenType.GREATER_EQUAL, ">=", null, 1),
                        new Expr.Literal(2)),
                new Expr.Binary(
                        new Expr.Literal(3),
                        new Token(TokenType.PLUS, "+", null, 1),
                        new Expr.Literal(4)
                ),
                new Expr.Binary(
                        new Expr.Literal(6),
                        new Token(TokenType.PLUS, "+", null, 1),
                        new Expr.Literal(5)
                )
        );
        System.out.println(new AstPrinter().print(expression));
    }

}
