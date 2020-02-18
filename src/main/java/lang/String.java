package lang;

import com.sun.istack.internal.NotNull;

import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author wangwenwen
 * @date 2020/1/27
 * @version 1.0.0
 */

/**
 * String类是字符串序列
 * String串是常量，值不可变，便于分享
 * 该类可用于字串的比较、查找、分割、大小写转换等
 * String默认使用UTF-16格式存储字串
 * 用最终final修饰，出于效率和安全考虑
 */

/**
 * The {@code String} class represents character strings. All
 * string literals in Java programs, such as {@code "abc"}, are
 * implemented as instances of this class.
 * <p>
 * Strings are constant; their values cannot be changed after they
 * are created. String buffers support mutable strings.
 * Because String objects are immutable they can be shared. For example:
 * <blockquote><pre>
 *     String str = "abc";
 * </pre></blockquote><p>
 * is equivalent to:
 * <blockquote><pre>
 *     char data[] = {'a', 'b', 'c'};
 *     String str = new String(data);
 * </pre></blockquote><p>
 * Here are some more examples of how strings can be used:
 * <blockquote><pre>
 *     System.out.println("abc");
 *     String cde = "cde";
 *     System.out.println("abc" + cde);
 *     String c = "abc".substring(2,3);
 *     String d = cde.substring(1, 2);
 * </pre></blockquote>
 * <p>
 * The class {@code String} includes methods for examining
 * individual characters of the sequence, for comparing strings, for
 * searching strings, for extracting substrings, and for creating a
 * copy of a string with all characters translated to uppercase or to
 * lowercase. Case mapping is based on the Unicode Standard version
 * specified by the {@link java.lang.Character Character} class.
 * <p>
 * The Java language provides special support for the string
 * concatenation operator (&nbsp;+&nbsp;), and for conversion of
 * other objects to strings. String concatenation is implemented
 * through the {@code StringBuilder}(or {@code StringBuffer})
 * class and its {@code append} method.
 * String conversions are implemented through the method
 * {@code toString}, defined by {@code Object} and
 * inherited by all classes in Java. For additional information on
 * string concatenation and conversion, see Gosling, Joy, and Steele,
 * <i>The Java Language Specification</i>.
 *
 * <p> Unless otherwise noted, passing a <tt>null</tt> argument to a constructor
 * or method in this class will cause a {@link NullPointerException} to be
 * thrown.
 *
 * <p>A {@code String} represents a string in the UTF-16 format
 * in which <em>supplementary characters</em> are represented by <em>surrogate
 * pairs</em> (see the section <a href="Character.html#unicode">Unicode
 * Character Representations</a> in the {@code Character} class for
 * more information).
 * Index values refer to {@code char} code units, so a supplementary
 * character uses two positions in a {@code String}.
 * <p>The {@code String} class provides methods for dealing with
 * Unicode code points (i.e., characters), in addition to those for
 * dealing with Unicode code units (i.e., {@code char} values).
 *
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Martin Buchholz
 * @author  Ulf Zibis
 * @see     java.lang.Object#toString()
 * @see     java.lang.StringBuffer
 * @see     java.lang.StringBuilder
 * @see     java.nio.charset.Charset
 * @since   JDK1.0
 */
public final class String
        implements Serializable, Comparable<String>,CharSequence {

    /** The value is used for character storage.
     * 不可变的真实原因在此
     * */
    private final char value[];

    /** Cache the hash code for the string */
    private int hash;

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -6849794470754667710L;

    /**
     * Class String is special cased within the Serialization Stream Protocol.
     *
     * A String instance is written into an ObjectOutputStream according to
     * <a href="{@docRoot}/../platform/serialization/spec/output.html">
     * Object Serialization Specification, Section 6.2, "Stream Elements"</a>
     */
    //FIXME
    private static final ObjectStreamField[] serialPersistentFields =
            new ObjectStreamField[0];

    /**
     * 初始化空char数组
     */
    /**
     * Initializes a newly created {@code String} object so that it represents
     * an empty character sequence.  Note that use of this constructor is
     * unnecessary since Strings are immutable.
     */
    //FIXME
    public String() { this.value = "".value; }
    //public String() {this.value = new char[0];}

    /**
     * Initializes a newly created {@code String} object so that it represents
     * the same sequence of characters as the argument; in other words, the
     * newly created string is a copy of the argument string. Unless an
     * explicit copy of {@code original} is needed, use of this constructor is
     * unnecessary since Strings are immutable.
     *
     * @param  original
     *         A {@code String}
     */
    public String(String original) {
        this.value = original.value;
        this.hash = original.hash;
    }

    /**
     * Allocates a new {@code String} so that it represents the sequence of
     * characters currently contained in the character array argument. The
     * contents of the character array are copied; subsequent modification of
     * the character array does not affect the newly created string.
     *
     * @param  value
     *         The initial value of the string
     */
    public String(char value[]) { this.value = Arrays.copyOf(value, value.length); }

    /**
     * Allocates a new {@code String} that contains characters from a subarray
     * of the character array argument. The {@code offset} argument is the
     * index of the first character of the subarray and the {@code count}
     * argument specifies the length of the subarray. The contents of the
     * subarray are copied; subsequent modification of the character array does
     * not affect the newly created string.
     *
     * @param  value
     *         Array that is the source of characters
     *
     * @param  offset
     *         The initial offset
     *
     * @param  count
     *         The length
     *
     * @throws  IndexOutOfBoundsException
     *          If the {@code offset} and {@code count} arguments index
     *          characters outside the bounds of the {@code value} array
     *          根据字符数组、起始位置、长度创建字符串
     */
    public String( char value[], int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (count <= 0) {
            if (count < 0) {
                throw new StringIndexOutOfBoundsException(count);
            }
            // count = 0 赋值空串
            if (offset <= value.length) {
                this.value = "".value;
                //this.value = new char[0];
                return;
            }
        }
        //this.value = new char[0];
        // note: offset or count might be near -1>>>1
        //-1 -> 1000 0001  补码：1111 1111 无符号右移一位 0111 1111.... 所以就是整型最大值
        if (offset > value.length - count) {
            throw  new StringIndexOutOfBoundsException(offset + count);
        }
        this.value = Arrays.copyOfRange(value, offset, offset+count);
    }

    /**
     * Allocates a new {@code String} that contains characters from a subarray
     * of the <a href="Character.html#unicode">Unicode code point</a> array
     * argument.  The {@code offset} argument is the index of the first code
     * point of the subarray and the {@code count} argument specifies the
     * length of the subarray.  The contents of the subarray are converted to
     * {@code char}s; subsequent modification of the {@code int} array does not
     * affect the newly created string.
     *
     * @param  codePoints
     *         Array that is the source of Unicode code points
     *
     * @param  offset
     *         The initial offset
     *
     * @param  count
     *         The length
     *
     * @throws  IllegalArgumentException
     *          If any invalid Unicode code point is found in {@code
     *          codePoints}
     *
     * @throws  IndexOutOfBoundsException
     *          If the {@code offset} and {@code count} arguments index
     *          characters outside the bounds of the {@code codePoints} array
     *
     * @since  1.5
     */
    public String(int[] codePoints, int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (count <= 0) {
            if (count < 0) {
                throw new StringIndexOutOfBoundsException(count);
            }
            if (offset <= codePoints.length) {
                this.value = "".value;
                return;
            }
        }
        //note: offset or count might be near -1>>>1
        if (offset > codePoints.length - count) {
            throw new StringIndexOutOfBoundsException(offset + count);
        }

        final int end = offset + count;

        //pass 1: Compute precise size of char[]
        //如果码点是bmp平面内 跳过循环 不是平面内 但是是unicode 扩容
        int n = count;
        for (int i = offset; i < end; i++) {
            int c = codePoints[i];
            if (Character.isBmpCodePoint(c))
                continue;
            else if (Character.isValidCodePoint(c))
                n++;
            else throw new IllegalArgumentException(Integer.toString(c));
        }

        /**
         * Surrogate这个概念，不是来自Java语言，而是来自Unicode编码方式之一:UTF-16。
         * 具体请见： <a href="https://en.wikipedia.org/wiki/UTF-16">UTF-16</a>
         * 简而言之，Java语言內部的字符信息是使用UTF-16编码。因为char这个类型是16-bit的，
         * 它可以有65536种取值，即65536个编号，每个编号可以代表1种字符。但是Unicode
         * 包含的字符已经远远超过65536个。那编号大于65536的，还要用16-bit编码，该怎么办？
         * 于是Unicode标准制定组想出的办法就是，从这65536个编号里，拿出2048个，规定它们是「Surrogates」，
         * 让它们两个为一组，来代表编号大于65536的那些字符。更具体地，
         * 编号从U+D800至U+DBFF的规定为「High Surrogates」，共1024个。
         * 编号为 U+DC00至U+DFFF的规定为「Low Surrogates」，也是1024个，
         * 它们两两组合出现，就又可以多表示1048576种字符。
         */
        //pass 2: Allocate and fill in char[]
        final char[] v = new char[n];

        for (int i = offset, j = 0; j < end; i++, j++) {
            int c = codePoints[i];
            if (Character.isBmpCodePoint(c))
                v[j] = (char)c;
            else
                Character.toSurrogates(c, v, j++);
        }

        this.value = v;
    }

    /**
     * Allocates a new {@code String} constructed from a subarray of an array
     * of 8-bit integer values.
     *
     * <p> The {@code offset} argument is the index of the first byte of the
     * subarray, and the {@code count} argument specifies the length of the
     * subarray.
     *
     * <p> Each {@code byte} in the subarray is converted to a {@code char} as
     * specified in the method above.
     *
     * @deprecated This method does not properly convert bytes into characters.
     * As of JDK&nbsp;1.1, the preferred way to do this is via the
     * {@code String} constructors that take a {@link
     * java.nio.charset.Charset}, charset name, or that use the platform's
     * default charset.
     *
     * @param  ascii
     *         The bytes to be converted to characters
     *
     * @param  hibyte
     *         The top 8 bits of each 16-bit Unicode code unit
     *
     * @param  offset
     *         The initial offset
     * @param  count
     *         The length
     *
     * @throws  IndexOutOfBoundsException
     *          If the {@code offset} or {@code count} argument is invalid
     *
     * @see  #String(byte[], int)
     * @see  #String(byte[], int, int, java.lang.String)
     * @see  #String(byte[], int, int, java.nio.charset.Charset)
     * @see  #String(byte[], int, int)
     * @see  #String(byte[], java.lang.String)
     * @see  #String(byte[], java.nio.charset.Charset)
     * @see  #String(byte[])
     */
    @Deprecated
    public String(byte ascii[], int hibyte, int offset, int count) {
        checkBounds(ascii, offset, count);
        char value[] = new char[count];

        if (hibyte == 0) {
            for (int i = count; i-- > 0;) {
                value[i] = (char)(ascii[i +offset] & 0xff);
            }
        } else {
            hibyte <<= 8;
            for (int i = count; i-- > 0;) {
                value[i] = (char)(hibyte | (ascii[i + offset] & 0xff));
            }
        }
        this.value = value;
    }

    /**
     * Common private utility method used to bounds check the byte array
     * and requested offset & length values used by the String(byte[],..)
     * constructor.
     * @return
     */
    private static void checkBounds(byte[] bytes, int offset, int length) {
        if (length < 0)
            throw new StringIndexOutOfBoundsException(length);
        if (offset < 0)
            throw new StringIndexOutOfBoundsException(offset);
        if (offset > bytes.length - length)
            throw new StringIndexOutOfBoundsException(offset + length);
    }















    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }
}
