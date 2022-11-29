package TP2;

import TP2.ASD.type.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This file contains the symbol table definition.
 * </p>
 * 
 * A symbol table contains a set of ident and the
 * corresponding symbols.
 * 
 * It can have a parent, containing itself other
 * symbols. If a symbol is not found, the request
 * is forwarded to the parent.
 */
public class SymbolTable {
  /**
   * Define different symbols
   */
  public abstract static class Symbol {
    String ident; // minimum, used in the storage map

    public String getIdent() {
      return ident;
    }
  }

  public static class VariableSymbol extends Symbol {
    private final Type type;

    /**
     * A variable symbol
     * @param type the type of the symbol
     * @param ident the name of the symbol
     */
    public VariableSymbol(Type type, String ident) {
      this.type = type;
      this.ident = ident;
    }

    public Type getType() {
      return type;
    }

    @Override
    public String toString() {
      return "\tname: " + ident + "\n\ttype: " + type.toString();
    }

    @Override public boolean equals(Object obj) {
      if(obj == null) return false;
      if(obj == this) return true;
      if(!(obj instanceof VariableSymbol)) return false;
      VariableSymbol o = (VariableSymbol) obj;
      return o.type.equals(this.type) &&
        o.ident.equals(this.ident);
    }
  }

  public static class ArgumentVariableSymbol extends VariableSymbol {

    private final String nameToUse;

    /**
     * A variable symbol
     *
     * @param type  the type of the symbol
     * @param ident the name of the symbol as marked in vsl
     * @param nameToUse the name of the symbol to use in ir
     */
    public ArgumentVariableSymbol(Type type, String ident, String nameToUse) {
      super(type, ident);
      this.nameToUse = nameToUse;
    }

    public String getNameToUse() {
      return nameToUse;
    }
  }

  public static class FunctionSymbol extends Symbol {
    Type returnType;
    /**
     * arguments is an ordered list of VariableSymbol
     */
    List<VariableSymbol> arguments;
    /**
     * false if declared but not defined
     */
    boolean defined; 

    /**
     * Declares a function symbol
     * @param returnType return type of the function
     * @param ident name of the function
     * @param arguments list of arguments of the function. 
     * @param defined false if declared but not defined
     */
    public FunctionSymbol(Type returnType, String ident, List<VariableSymbol> arguments, boolean defined) {
      this.returnType = returnType;
      this.ident = ident;
      this.arguments = arguments;
      this.defined = defined;
    }

    public boolean isDefined() {
      return defined;
    }

    public Type getReturnType() {
      return returnType;
    }

    public List<VariableSymbol> getArguments() {
      return arguments;
    }

    @Override public boolean equals(Object obj) {
      if(obj == null) return false;
      if(obj == this) return true;
      if(!(obj instanceof FunctionSymbol)) return false;
      FunctionSymbol o = (FunctionSymbol) obj;
      return o.returnType.equals(this.returnType) &&
        o.ident.equals(this.ident) &&
        o.arguments.equals(this.arguments) &&
        o.defined == this.defined;
    }
  }

  
  /**
   * Store the table as a map
   */
  private Map<String, Symbol> table;
  /**
   * Parent table
   */
  private SymbolTable parent;

  /**
   * Construct a new symbol table
   */
  public SymbolTable() {
    this.table = new HashMap<>();
    this.parent = null;
  }

  /**
   * <p>
   * Construct a new symbol table with a parent 
   * </p>
   * 
   * Think of the parent table as the symbols declared outside the current block and this.table as the symbols declared in this block.
   * @param parent the parent symbol table.
   */
  public SymbolTable(SymbolTable parent) {
    this.table = new HashMap<>();
    this.parent = parent;
  }

  /**
   * Add a new symbol
   * @param sym the symbol to add
   * @return false if the symbol cannot be added (already in the scope)
   */
  public boolean add(Symbol sym) {
    Symbol res = this.table.get(sym.ident);
    if(res != null) {
      return false;
    }

    this.table.put(sym.ident, sym);
    return true;
  }

  /**
   * Remove a symbol
   * @param ident the name of the symbol to remove
   * @return false if the symbol is not in the table (without looking at parent's)
   */
  public boolean remove(String ident) {
    return this.table.remove(ident) != null;
  }

  /**
   * Return the symbol corresponding to some name.
   * 
   * If not found, this function will forward its call to its parent
   * @param ident the name of the symbol
   * @return the symbol if found or null
   */
  public Symbol lookup(String ident) {
    Symbol res = this.table.get(ident);

    if((res == null) && (this.parent != null)) {
      // Forward request
      return this.parent.lookup(ident);
    }

    return res; // Either the symbol or null
  }

  @Override public boolean equals(Object obj) {
    if(obj == null) return false;
    if(obj == this) return true;
    if(!(obj instanceof SymbolTable)) return false;
    SymbolTable o = (SymbolTable) obj;
    return o.table.equals(this.table) &&
      ((o.parent == null && this.parent == null) || o.parent.equals(this.parent));
  }
}
