package token

type TokenType string

type Token struct {
	Type    TokenType
	Literal string
}

const (
	ILLEGAL = "ILLEGAL"
	EOF     = "EOF"

	IDENT = "IDENT"
	INT   = "INT"

	ASSIGN   = "="
	PLUS     = "+"
	MINUS    = "-"
	BANG     = "!"
	ASTERISK = "*"
	SLASH    = "/"

	LT = "<"
	GT = ">"

	COMMA     = ","
	SEMICOLON = ";"

	LPAREN = "("
	RPAREN = ")"
	LBRACE = "{"
	RBRACE = "}"

	FUNCTION = "FUNCTION"
	LET      = "LET"
	TRUE     = "TRUE"
	FALSE    = "FALSE"
	IF       = "IF"
	ELSE     = "ELSE"
	RETURN   = "RETURN"
)

var keyWords = map[string]TokenType{
	"fn":     FUNCTION,
	"let":    LET,
	"TRUE":   TRUE,
	"FALSE":  FALSE,
	"IF":     IF,
	"ELSE":   ELSE,
	"RETURN": RETURN,
}

func LookupIdent(ident string) TokenType {
	if tok, ok := keyWords[ident]; ok {
		return tok
	}
	return IDENT
}
