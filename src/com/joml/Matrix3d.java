/*
 * (C) Copyright 2015 Richard Greenlees
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 *  1) The above copyright notice and this permission notice shall be included
 *     in all copies or substantial portions of the Software.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package com.joml;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.nio.DoubleBuffer;

/**
 * Matrix3f
 * 
 * Contains the definition of a 3x3 Matrix of doubles, and associated functions to transform
 * it. The matrix is column-major to match OpenGL's interpretation, and it looks like this:
 * 
 *      m00  m10  m20
 *      m01  m11  m21
 *      m02  m12  m22
 * 
 * @author Richard Greenlees
 */
public class Matrix3d implements Serializable, Externalizable {

    public double m00;
    public double m01;
    public double m02;
    public double m10;
    public double m11;
    public double m12;
    public double m20;
    public double m21;
    public double m22;

    public Matrix3d() {
        super();
        identity();
    }

    public Matrix3d(double diagonal) {
        super();
        this.m00 = diagonal;
        this.m11 = diagonal;
        this.m22 = diagonal;
    }

    public Matrix3d(Matrix3d mat) {
        this.m00 = mat.m00;
        this.m01 = mat.m01;
        this.m02 = mat.m02;
        this.m10 = mat.m10;
        this.m11 = mat.m11;
        this.m12 = mat.m12;
        this.m20 = mat.m20;
        this.m21 = mat.m21;
        this.m22 = mat.m22;
    }

    public Matrix3d(Matrix3f mat) {
        this.m00 = mat.m00;
        this.m01 = mat.m01;
        this.m02 = mat.m02;
        this.m10 = mat.m10;
        this.m11 = mat.m11;
        this.m12 = mat.m12;
        this.m20 = mat.m20;
        this.m21 = mat.m21;
        this.m22 = mat.m22;
    }

    public Matrix3d(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    /** Set the values in this matrix to the ones in m1 */
    public void set(Matrix3d m1) {
        m00 = m1.m00;
        m01 = m1.m01;
        m02 = m1.m02;
        m10 = m1.m10;
        m11 = m1.m11;
        m12 = m1.m12;
        m20 = m1.m20;
        m21 = m1.m21;
        m22 = m1.m22;
    }

    /** Set the values in this matrix to the ones in m1 */
    public void set(Matrix3f m1) {
        m00 = m1.m00;
        m01 = m1.m01;
        m02 = m1.m02;
        m10 = m1.m10;
        m11 = m1.m11;
        m12 = m1.m12;
        m20 = m1.m20;
        m21 = m1.m21;
        m22 = m1.m22;
    }

    /** Multiplies this matrix by the supplied matrix. This matrix will be the left-sided one */
    public void mul(Matrix3d right) {
        set( this.m00 * right.m00 + this.m10 * right.m01 + this.m20 * right.m02,
             this.m01 * right.m00 + this.m11 * right.m01 + this.m21 * right.m02,
             this.m02 * right.m00 + this.m12 * right.m01 + this.m22 * right.m02,
             this.m00 * right.m10 + this.m10 * right.m11 + this.m20 * right.m12,
             this.m01 * right.m10 + this.m11 * right.m11 + this.m21 * right.m12,
             this.m02 * right.m10 + this.m12 * right.m11 + this.m22 * right.m12,
             this.m00 * right.m20 + this.m10 * right.m21 + this.m20 * right.m22,
             this.m01 * right.m20 + this.m11 * right.m21 + this.m21 * right.m22,
             this.m02 * right.m20 + this.m12 * right.m21 + this.m22 * right.m22 );
    }

    /** Multiplies this matrix by the supplied matrix. This matrix will be the left-sided one */
    public void mul(Matrix3f right) {
        set( this.m00 * right.m00 + this.m10 * right.m01 + this.m20 * right.m02,
             this.m01 * right.m00 + this.m11 * right.m01 + this.m21 * right.m02,
             this.m02 * right.m00 + this.m12 * right.m01 + this.m22 * right.m02,
             this.m00 * right.m10 + this.m10 * right.m11 + this.m20 * right.m12,
             this.m01 * right.m10 + this.m11 * right.m11 + this.m21 * right.m12,
             this.m02 * right.m10 + this.m12 * right.m11 + this.m22 * right.m12,
             this.m00 * right.m20 + this.m10 * right.m21 + this.m20 * right.m22,
             this.m01 * right.m20 + this.m11 * right.m21 + this.m21 * right.m22,
             this.m02 * right.m20 + this.m12 * right.m21 + this.m22 * right.m22 );
    }

    /** Multiplies the left matrix by the right, and stores the results in dest. Does not modify the left or right matrices */
    public static void mul(Matrix3d left, Matrix3d right, Matrix3d dest) {
        dest.set( left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                  left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                  left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                  left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                  left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                  left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                  left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                  left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                  left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 );
    }

    /** Multiplies the left matrix by the right, and stores the results in dest. Does not modify the left or right matrices */
    public static void mul(Matrix3f left, Matrix3d right, Matrix3d dest) {
        dest.set( left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                  left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                  left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                  left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                  left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                  left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                  left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                  left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                  left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 );
    }

    /** Multiplies the left matrix by the right, and stores the results in dest. Does not modify the left or right matrices */
    public static void mul(Matrix3d left, Matrix3f right, Matrix3d dest) {
        dest.set( left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02,
                  left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02,
                  left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02,
                  left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12,
                  left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12,
                  left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12,
                  left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22,
                  left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22,
                  left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22 );
    }

    /** Multiplies the left matrix by the right, and stores the results in dest. Does not modify the left or right matrices. 
    * <B>This is not alias safe so make sure dest is not the same object as the original or you WILL get incorrect results!</B> */
    public static void mulFast(Matrix3d left, Matrix3d right, Matrix3d dest) {
        dest.m00 = left.m00 * right.m00 + left.m10 * right.m01 + left.m20 * right.m02;
        dest.m01 = left.m01 * right.m00 + left.m11 * right.m01 + left.m21 * right.m02;
        dest.m02 = left.m02 * right.m00 + left.m12 * right.m01 + left.m22 * right.m02;
        dest.m10 = left.m00 * right.m10 + left.m10 * right.m11 + left.m20 * right.m12;
        dest.m11 = left.m01 * right.m10 + left.m11 * right.m11 + left.m21 * right.m12;
        dest.m12 = left.m02 * right.m10 + left.m12 * right.m11 + left.m22 * right.m12;
        dest.m20 = left.m00 * right.m20 + left.m10 * right.m21 + left.m20 * right.m22;
        dest.m21 = left.m01 * right.m20 + left.m11 * right.m21 + left.m21 * right.m22;
        dest.m22 = left.m02 * right.m20 + left.m12 * right.m21 + left.m22 * right.m22;
    }

    /** Sets the values within this matrix to the supplied double values. The result looks like this:<br><br>
     * 
     * m00, m10, m20
     * m01, m11, m21
     * m02, m12, m22
     * 
     */
    public void set(double m00, double m01, double m02, double m10, double m11,
                    double m12, double m20, double m21, double m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    /** Sets the values in this matrix based on the supplied double array. The result looks like this:<br><br>
     * 
     * 0, 3, 6<br>
     * 1, 4, 7<br>
     * 2, 5, 8<br><br>
     * 
     * Only uses the first 9 values, all others are ignored
     * 
     */
    public void set(double m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m10 = m[3];
        m11 = m[4];
        m12 = m[5];
        m20 = m[6];
        m21 = m[7];
        m22 = m[8];
    }

    /** Sets the values in this matrix based on the supplied double array. The result looks like this:<br><br>
     * 
     * 0, 3, 6<br>
     * 1, 4, 7<br>
     * 2, 5, 8<br><br>
     * 
     * Only uses the first 9 values, all others are ignored
     * 
     */
    public void set(float m[]) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m10 = m[3];
        m11 = m[4];
        m12 = m[5];
        m20 = m[6];
        m21 = m[7];
        m22 = m[8];
    }

    /** Returns the determinant of this Matrix */
    public double determinant() {
        return   ((m00 * m11 * m22)
               + (m10 * m21 * m02)
               + (m20 * m01 * m12))
               - ((m20 * m11 * m02)
               + (m00 * m21 * m12)
               + (m10 * m01 * m22));
    }

    /** Inverts this matrix */
    public void invert() {
        double s = determinant();
        
        if (s == 0.0) {
            return;
        }
        s = 1.0 / s;

       set( (m11 * m22) - (m21 * m12) * s,
           -((m01 * m22) - (m21 * m02)) * s,
            (m01 * m12) - (m11 * m02) * s,
           -((m10 * m22) - (m20 * m12)) * s,
            (m00 * m22) - (m20 * m02) * s,
           -((m00 * m12) - (m10 * m02)) * s,
            (m10 * m21) - (m20 * m11) * s,
           -((m00 * m21) - (m20 * m01)) * s,
            (m00 * m11) - (m10 * m01) * s);
    }

    /** Inverts the source matrix and stores the results in dest. Does not modify the source */
    public static void invert(Matrix3d source, Matrix3d dest) {
        double s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        dest.set(  ((source.m11 * source.m22) - (source.m21 * source.m12)) * s,
                  -((source.m01 * source.m22) - (source.m21 * source.m02)) * s,
                   ((source.m01 * source.m12) - (source.m11 * source.m02)) * s,
                  -((source.m10 * source.m22) - (source.m20 * source.m12)) * s,
                   ((source.m00 * source.m22) - (source.m20 * source.m02)) * s,
                  -((source.m00 * source.m12) - (source.m10 * source.m02)) * s,
                   ((source.m10 * source.m21) - (source.m20 * source.m11)) * s,
                  -((source.m00 * source.m21) - (source.m20 * source.m01)) * s,
                   ((source.m00 * source.m11) - (source.m10 * source.m01)) * s  );
    }

    /** Inverts the source matrix and stores the results in dest. Does not modify the source
    * <B>This is not alias safe so make sure dest is not the same object as the original or you WILL get incorrect results!</B> */
    public static void invertFast(Matrix3d source, Matrix3d dest) {
        double s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        
        dest.m00 = ((source.m11 * source.m22) - (source.m21 * source.m12)) * s;
        dest.m01 = -((source.m01 * source.m22) - (source.m21 * source.m02)) * s;
        dest.m02 = ((source.m01 * source.m12) - (source.m11 * source.m02)) * s;
        dest.m10 = -((source.m10 * source.m22) - (source.m20 * source.m12)) * s;
        dest.m11 = ((source.m00 * source.m22) - (source.m20 * source.m02)) * s;
        dest.m12 = -((source.m00 * source.m12) - (source.m10 * source.m02)) * s;
        dest.m20 = ((source.m10 * source.m21) - (source.m20 * source.m11)) * s;
        dest.m21 = -((source.m00 * source.m21) - (source.m20 * source.m01)) * s;
        dest.m22 = ((source.m00 * source.m11) - (source.m10 * source.m01)) * s;
    }

    /** Inverts the source matrix and stores the results in dest. Does not modify the source
    * <B>This is not alias safe so make sure dest is not the same object as the original or you WILL get incorrect results!</B> */
    public static void invert(Matrix3d source, DoubleBuffer dest) {
        double s = source.determinant();
        if (s == 0.0f) {
            return;
        }
        s = 1.0f / s;
        
        dest.put(((source.m11 * source.m22) - (source.m21 * source.m12)) * s);
        dest.put(-((source.m01 * source.m22) - (source.m21 * source.m02)) * s);
        dest.put(((source.m01 * source.m12) - (source.m11 * source.m02)) * s);
        dest.put(-((source.m10 * source.m22) - (source.m20 * source.m12)) * s);
        dest.put(((source.m00 * source.m22) - (source.m20 * source.m02)) * s);
        dest.put(-((source.m00 * source.m12) - (source.m10 * source.m02)) * s);
        dest.put(((source.m10 * source.m21) - (source.m20 * source.m11)) * s);
        dest.put(-((source.m00 * source.m21) - (source.m20 * source.m01)) * s);
        dest.put(((source.m00 * source.m11) - (source.m10 * source.m01)) * s);
    }

    /** Transposes this matrix */
    public void transpose() {
        set(m00, m10, m20,
            m01, m11, m21,
            m02, m12, m22);
    }

    /** Transposes the supplied original matrix and stores the results in dest. The original is not modified */
    public static void transpose(Matrix3d original, Matrix3d dest) {
        dest.set(original.m00, original.m10, original.m20,
                 original.m01, original.m11, original.m21,
                 original.m02, original.m12, original.m22);
    }

    /** Transposes the supplied original matrix and stores the results in dest. The original is not modified */
    public static void transpose(Matrix3d original, DoubleBuffer dest) {
        dest.put(original.m00);
        dest.put(original.m10);
        dest.put(original.m20);
        dest.put(original.m01);
        dest.put(original.m11);
        dest.put(original.m21);
        dest.put(original.m02);
        dest.put(original.m12);
        dest.put(original.m22);
    }

    /** Transposes the supplied original matrix and stores the results in dest. The original is not modified.
    * <B>This is not alias safe so make sure dest is not the same object as the original or you WILL get incorrect results!</B> */
    public static void transposeFast(Matrix3d original, Matrix3d dest) {
        dest.m00 = original.m00;
        dest.m01 = original.m10;
        dest.m02 = original.m20;
        dest.m10 = original.m01;
        dest.m11 = original.m11;
        dest.m12 = original.m21;
        dest.m20 = original.m02;
        dest.m21 = original.m12;
        dest.m22 = original.m22;
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public void translation(double x, double y) {
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m20 = x;
        this.m21 = y;
        this.m22 = 1.0;
    }
 
    /**
     * Set the given matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(double x, double y, Matrix3d dest) {
    	dest.translation(x, y);
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public void translation(Vector2d position) {
        translation(position.x, position.y);
    }

    /**
     * Set the given matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(Vector2d position, Matrix3d dest) {
        dest.translation(position.x, position.y);
    }

    /**
     * Set this matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public void translation(Vector2f position) {
        translation(position.x, position.y);
    }

    /**
     * Set the given matrix to be a simple translation matrix.
     * <p>
     * The resulting matrix can be multiplied against another transformation
     * matrix to obtain an additional translation.
     */
    public static void translation(Vector2f position, Matrix3d dest) {
        dest.translation(position.x, position.y);
    }

    /** Multiply the supplied Matrix by the supplied scalar value and store the results in dest. Does not modify the source */
    public static void mul(Matrix3d source, double scalar, Matrix3d dest) {
        dest.m00 = source.m00 * scalar;
        dest.m01 = source.m01 * scalar;
        dest.m02 = source.m02 * scalar;
        dest.m10 = source.m10 * scalar;
        dest.m11 = source.m11 * scalar;
        dest.m12 = source.m12 * scalar;
        dest.m20 = source.m20 * scalar;
        dest.m21 = source.m21 * scalar;
        dest.m22 = source.m22 * scalar;
    }

    /** Multiply the supplied Matrix by the supplied scalar value and store the results in dest. Does not modify the source */
    public static void mul(Matrix3d source, double scalar, DoubleBuffer dest) {
        dest.put(source.m00 * scalar);
        dest.put(source.m01 * scalar);
        dest.put(source.m02 * scalar);
        dest.put(source.m10 * scalar);
        dest.put(source.m11 * scalar);
        dest.put(source.m12 * scalar);
        dest.put(source.m20 * scalar);
        dest.put(source.m21 * scalar);
        dest.put(source.m22 * scalar);
    }

    public String toString() {
        return "Matrix3d { " + this.m00 + ", " + this.m10 + ", " + this.m20 + ",\n"
                + "           " + this.m01 + ", " + this.m11 + ", " + this.m21 + ",\n"
                + "           " + this.m02 + ", " + this.m12 + ", " + this.m22 + " }\n";
    }

    /** Stores this matrix in the supplied DoubleBuffer */
    public void store(DoubleBuffer buffer) {
        buffer.put(this.m00);
        buffer.put(this.m01);
        buffer.put(this.m02);
        buffer.put(this.m10);
        buffer.put(this.m11);
        buffer.put(this.m12);
        buffer.put(this.m20);
        buffer.put(this.m21);
        buffer.put(this.m22);
    }

    /** Sets all the values within this matrix to 0 */
    public void zero() {
        this.m00 = 0.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = 0.0;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 0.0;
    }
    
    /** Sets this matrix to the identity */
    public void identity() {
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
    }

    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     * 			the scale in x
     * @param y
     * 			the scale in y
     * @param z
     * 			the scale in z
     */
    public void scale(double x, double y, double z) {
    	identity();
        m00 = x;
        m11 = y;
        m22 = z;
    }
    
    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param scale
     * 			the scale applied to each dimension
     */
    public void scale(Vector3d scale) {
    	identity();
        m00 = scale.x;
        m11 = scale.y;
        m22 = scale.z;
    }
    
    /**
     * Set the given matrix <code>dest</code> to be a simple scale matrix.
     * 
     * @param scale
     * 			the scale applied to each dimension
     */
    public static void scale(Vector3d scale, Matrix3d dest) {
    	dest.identity();
        dest.m00 = scale.x;
        dest.m11 = scale.y;
        dest.m22 = scale.z;
    }
    
    /**
     * Set this matrix to be a simple scale matrix.
     * 
     * @param x
     * 			the scale in x
     * @param y
     * 			the scale in y
     * @param z
     * 			the scale in z
     */
    public void scale(double x, double y, double z, Matrix3d dest) {
    	dest.identity();
    	dest.m00 = x;
    	dest.m11 = y;
    	dest.m22 = z;
    }
    
    /**
     * Set this matrix to a rotation matrix which rotates the given radians about a given axis.
     * 
     * From <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     */
    public void rotation(double angle, Vector3d axis) {
    	double cos = Math.cos(angle);
    	double sin = Math.sin(angle);
    	m00 = cos + axis.x * axis.x * (1.0 - cos);
    	m10 = axis.x * axis.y * (1.0 - cos) - axis.z * sin;
    	m20 = axis.x * axis.z * (1.0 - cos) + axis.y * sin;
    	m01 = axis.y * axis.x * (1.0 - cos) + axis.z * sin;
    	m11 = cos + axis.y * axis.y * (1.0 - cos);
    	m21 = axis.y * axis.z * (1.0 - cos) - axis.x * sin;
    	m02 = axis.z * axis.x * (1.0 - cos) - axis.y * sin;
    	m12 = axis.z * axis.y * (1.0 - cos) + axis.x * sin;
    	m22 = cos + axis.z * axis.z * (1.0 - cos);
    }
    
    /**
     * Set the destination matrix to a rotation matrix which rotates the given radians about a given axis.
     * 
     * From <a href="http://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle">Wikipedia</a>
     */
    public static void rotation(double angle, Vector3d axis, Matrix3d dest) {
    	double cos = Math.cos(angle);
    	double sin = Math.sin(angle);
    	dest.m00 = cos + axis.x * axis.x * (1.0 - cos);
    	dest.m10 = axis.x * axis.y * (1.0 - cos) - axis.z * sin;
    	dest.m20 = axis.x * axis.z * (1.0 - cos) + axis.y * sin;
    	dest.m01 = axis.y * axis.x * (1.0 - cos) + axis.z * sin;
    	dest.m11 = cos + axis.y * axis.y * (1.0 - cos);
    	dest.m21 = axis.y * axis.z * (1.0 - cos) - axis.x * sin;
    	dest.m02 = axis.z * axis.x * (1.0 - cos) - axis.y * sin;
    	dest.m12 = axis.z * axis.y * (1.0 - cos) + axis.x * sin;
    	dest.m22 = cos + axis.z * axis.z * (1.0 - cos);
    }

    public void transform(Vector3d v) {
        v.mul(this);
    }

    public static void transform(Matrix3d mat, Vector3d v) {
        v.mul(mat);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(m00);
        out.writeDouble(m01);
        out.writeDouble(m02);
        out.writeDouble(m10);
        out.writeDouble(m11);
        out.writeDouble(m12);
        out.writeDouble(m20);
        out.writeDouble(m21);
        out.writeDouble(m22);
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        m00 = in.readDouble();
        m01 = in.readDouble();
        m02 = in.readDouble();
        m10 = in.readDouble();
        m11 = in.readDouble();
        m12 = in.readDouble();
        m20 = in.readDouble();
        m21 = in.readDouble();
        m22 = in.readDouble();
    }

}