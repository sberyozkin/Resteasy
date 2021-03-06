package org.jboss.resteasy.util;

import javax.json.bind.annotation.JsonbAnnotation;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbNumberFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.annotation.JsonbTypeSerializer;
import javax.json.bind.annotation.JsonbVisibility;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@SuppressWarnings("unchecked")
public final class FindAnnotation
{

   /**
    *
    */
   private static final Class<? extends Annotation>[] JAXRS_ANNOTATIONS =
           (Class<? extends Annotation>[]) new Class[]{
                   QueryParam.class,
                   HeaderParam.class,
                   CookieParam.class,
                   PathParam.class,
                   MatrixParam.class,
                   FormParam.class,
                   Context.class,
                   org.jboss.resteasy.annotations.jaxrs.QueryParam.class,
                   org.jboss.resteasy.annotations.jaxrs.HeaderParam.class,
                   org.jboss.resteasy.annotations.jaxrs.CookieParam.class,
                   org.jboss.resteasy.annotations.jaxrs.PathParam.class,
                   org.jboss.resteasy.annotations.jaxrs.MatrixParam.class,
                   org.jboss.resteasy.annotations.jaxrs.FormParam.class,
           };

   @SuppressWarnings("rawtypes")
   private static final Class[] findJaxRSAnnotations_TYPE = new Class[]{};


   private static final Class<? extends Annotation>[] JSON_BINDING_ANNOTATIONS =
           (Class<? extends Annotation>[]) new Class[]{
                   JsonbCreator.class,
                   JsonbNillable.class,
                   JsonbNumberFormat.class,
                   JsonbVisibility.class,
                   JsonbPropertyOrder.class,
                   JsonbTypeAdapter.class,
                   JsonbTypeSerializer.class,
                   JsonbProperty.class,
                   JsonbDateFormat.class,
                   JsonbTransient.class,
                   JsonbTypeDeserializer.class,
                   JsonbAnnotation.class
           };

   private FindAnnotation()
   {
   }

   /**
    * Finds annotation.
    *
    * @param <T> type
    * @param searchList array of annotations
    * @param annotation expected annotation class
    * @return annotation or null
    */
   public static <T> T findAnnotation(Annotation[] searchList, Class<T> annotation)
   {
      if (searchList == null) return null;
      for (Annotation ann : searchList)
      {
         if (ann.annotationType().equals(annotation))
         {
            return (T) ann;
         }
      }
      return null;
   }


   /**
    * Finds annotation.
    *
    * @param <T> type
    * @param searchList array of annotations
    * @param annotations expected annotations
    * @return annotation or null
    */
   public static <T> T findAnnotation(Annotation[] searchList, String... annotations)
   {
      if (searchList == null || annotations == null || annotations.length == 0) return null;
      for (Annotation ann : searchList)
      {
         for (String a : annotations)
         {
            if (ann.annotationType().getName().equals(a))
            {
               return (T) ann;
            }
         }
      }
      return null;
   }


   public static Class<? extends Annotation>[] findJaxRSAnnotations(Annotation[] searchList)
   {

      LinkedList<Class<? extends Annotation>> result = new LinkedList<Class<? extends Annotation>>();

      for (Class<? extends Annotation> clazz : JAXRS_ANNOTATIONS)
      {

         if (findAnnotation(searchList, clazz) != null)
            result.add(clazz);

      }

      return result.toArray(findJaxRSAnnotations_TYPE);

   }

   public static Class<? extends Annotation>[] findJsonBindingAnnotations(Annotation[] searchList)
   {

      LinkedList<Class<? extends Annotation>> result = new LinkedList<Class<? extends Annotation>>();

      for (Class<? extends Annotation> clazz : JSON_BINDING_ANNOTATIONS)
      {

         if (findAnnotation(searchList, clazz) != null)
            result.add(clazz);

      }

      return result.toArray(findJaxRSAnnotations_TYPE);

   }

   /**
    * Returns an array of annotations the specified method of
    * a resource class.
    *
    * @param method method
    * @return array of annotations
    */
   public static Annotation[] getResourcesAnnotations(Method method)
   {
      Map<Class<?>, Annotation> annotations = new HashMap<Class<?>, Annotation>();
      for (Annotation annotation : method.getDeclaringClass().getAnnotations())
      {
         annotations.put(annotation.getClass(), annotation);
      }
      for (Annotation annotation : method.getAnnotations())
      {
         annotations.put(annotation.getClass(), annotation);
      }
      return annotations.values().toArray(new Annotation[annotations.size()]);
   }

   /**
    * Look for an annotation in a list of annotations.  If not there, see if it is on the type provided.
    *
    * @param <T> type
    * @param type type class
    * @param annotations array of annotations
    * @param annotation expected annotation
    * @return annotation or null
    */
   public static <T extends Annotation> T findAnnotation(Class<?> type, Annotation[] annotations, Class<T> annotation)
   {
      T config = FindAnnotation.findAnnotation(annotations, annotation);
      if (config == null)
      {
         config = type.getAnnotation(annotation);
      }
      return config;
   }


}
