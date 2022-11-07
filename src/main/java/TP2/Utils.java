package TP2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
  private static int tmp = 0;
  private static int lab = 0;
  private static int glob = 0;
  private final static Pattern re = Pattern.compile("\\\\n");

  /**
   * 
   * @param level the number of "tabs"
   * @return 4 spaces × level, useful for code indentation
   */
  public static String indent(int level) {
    StringBuilder r = new StringBuilder();
    while(level-- > 0)
      r.append("  ");
    return r.toString();
  }


  /**
   * Generate a new unique local identifier (starting with %)
   * @return the identifier
   */
  public static String newtmp() {
    tmp++;
    return "%tmp" + tmp;
  }
 
  /**
   * <p>
   * generate a new unique label starting with str
   * </p>
   * 
   * ex: newlab("if") may return "if24"
   * @param str the start of the label
   * @return the label name
   */
  public static String newlab(String str) {
    lab++;
    return str + lab;
  }

  /**
   * generate a new unique global identifier (starting with @tmp) *)
   * @param str the start of the name of the identifier
   * @return the name of the identifier
   */
  public static String newglob(String str) {
    glob++;
    return str + glob;
  }

  /**
   * Transform escaped newlines ('\' 'n') into newline form suitable for LLVM and append the NUL character (end of string)
   * @param str the input string
   * @return a pair: the new String, and its size (according to LLVM)
   */
  public static LLVMStringConstant stringTransform(String str) {
    Matcher m = re.matcher(str);
    StringBuffer res = new StringBuffer();
    int count = 0;

    while(m.find()) {
      m.appendReplacement(res, "\\\\0A");
      count++;
    }

    m.appendTail(res).append("\\00");

    // + 1 for \00
    // - 1 by \n because each ('\' '\n') is transformed into one char
    return new LLVMStringConstant(res.toString(), 1 + str.length() - count);
  }

  /**
   * Return type of stringTransform
   */
  public static class LLVMStringConstant {
    String str;
    int length;
    LLVMStringConstant(String str, int length) {
      this.str = str;
      this.length = length;
    }
  }
}
