package simpledb;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc {
    
    private Type[] _typeAr;
    private String[] _fieldAr;


    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields
     * fields, with the first td1.numFields coming from td1 and the remaining
     * from td2.
     * @param td1 The TupleDesc with the first fields of the new TupleDesc
     * @param td2 The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc combine(TupleDesc td1, TupleDesc td2) {
        Type[] typeAr = new Type[td1._typeAr.length+td2._typeAr.length];
        String[] fieldAr = null;

        System.arraycopy(td1._typeAr, 0, typeAr, 0, td1._typeAr.length);
        System.arraycopy(td2._typeAr, 0, typeAr, td1._typeAr.length, td2._typeAr.length);

        if (td1._fieldAr != null && td2._fieldAr != null){
            fieldAr = new String[td1._fieldAr.length + td2._fieldAr.length];
            System.arraycopy(td1._fieldAr, 0, fieldAr, 0, td1._fieldAr.length);
            System.arraycopy(td2._fieldAr, 0, fieldAr, td1._fieldAr.length, td2._fieldAr.length);
        }

        return new TupleDesc(typeAr, fieldAr);
    }

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     *
     * @param typeAr array specifying the number of and types of fields in
     *        this TupleDesc. It must contain at least one entry.
     * @param fieldAr array specifying the names of the fields. Note that names may be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        _typeAr = new Type[typeAr.length];
        _typeAr = Arrays.copyOf(typeAr, typeAr.length);
        if (fieldAr!=null){
            _fieldAr = new String[fieldAr.length];
            _fieldAr = Arrays.copyOf(fieldAr, fieldAr.length);
        }else{
            _fieldAr = null;
        }
    }

    /**
     * Constructor.
     * Create a new tuple desc with typeAr.length fields with fields of the
     * specified types, with anonymous (unnamed) fields.
     *
     * @param typeAr array specifying the number of and types of fields in
     *        this TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        _typeAr = new Type[typeAr.length];
        _typeAr = Arrays.copyOf(typeAr, typeAr.length);
        _fieldAr = null;
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        return _typeAr.length;
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     *
     * @param i index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        if (_fieldAr!=null){
            return _fieldAr[i];
        }else{
            return null;
        }
    }

    /**
     * Find the index of the field with a given name.
     *
     * @param name name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException if no field with a matching name is found.
     */
    public int nameToId(String name) throws NoSuchElementException {
        if (_fieldAr!=null){
            for (int i=0; i<_fieldAr.length; i++){
                if (_fieldAr[i].equals(name)){
                    return i;
                }
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     *
     * @param i The index of the field to get the type of. It must be a valid index.
     * @return the type of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public Type getType(int i) throws NoSuchElementException {
        return _typeAr[i];
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     * Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        int size = 0;
        for (Type t: _typeAr){
            size += t.getLen();
        }
        return size;
    }

    /**
     * Compares the specified object with this TupleDesc for equality.
     * Two TupleDescs are considered equal if they are the same size and if the
     * n-th type in this TupleDesc is equal to the n-th type in td.
     *
     * @param o the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        if (!(o instanceof TupleDesc))
            return false;

        TupleDesc that = (TupleDesc) o;
        if (_typeAr.length != that._typeAr.length)
            return false;
        for (int i = 0; i < _typeAr.length; i++) {
            if (_typeAr[i] != that._typeAr[i])
                return false;
        }
        return true;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * @return String describing this descriptor.
     */
    public String toString() {
        StringBuilder desString = new StringBuilder();

        for (int i=0; i<_typeAr.length; i++) {
            desString.append(_typeAr.toString());
            if (_fieldAr!=null)
                desString.append('(').append(_fieldAr[i]).append(')');
            if (i<_typeAr.length-1)
                desString.append(',');
        }
        return desString.toString();
    }
}
