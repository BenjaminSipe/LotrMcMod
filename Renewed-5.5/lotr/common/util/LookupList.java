package lotr.common.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LookupList extends AbstractList {
   private final List list = new ArrayList();
   private final Map lookup = new HashMap();
   private final Function keyExtractor;

   public LookupList(Function keyExtractor) {
      this.keyExtractor = keyExtractor;
   }

   public Object get(int index) {
      return this.list.get(index);
   }

   public Object set(int index, Object element) {
      this.list.set(index, element);
      this.lookup.put(this.keyExtractor.apply(element), element);
      return element;
   }

   public void add(int index, Object element) {
      this.list.add(index, element);
      this.lookup.put(this.keyExtractor.apply(element), element);
   }

   public Object remove(int index) {
      Object removed = this.list.remove(index);
      if (removed != null) {
         this.lookup.remove(this.keyExtractor.apply(removed));
      }

      return removed;
   }

   public Object lookup(Object key) {
      return this.lookup.get(key);
   }

   public boolean hasKey(Object key) {
      return this.lookup.containsKey(key);
   }

   public int size() {
      return this.list.size();
   }
}
