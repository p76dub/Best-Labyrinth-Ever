package model.interfaces;

/**
 * Un Network est un réseau d'éléments de même type. En plus des éléments (de type E), ces derniers sont reliés entre
 * eux par un type particulier (paramètre R)
 * @param <E> type des noeuds
 * @param <R> type des relations
 */
public interface INetwork<E,R> {

    // REQUETES
    /**
     * Obtenir l'élément relié à src avec la relation rel. Si aucun élément ne correspond, renvoie null.
     * @param src l'élément source
     * @param rel la relation
     * @return null ou un élément
     * @pre <pre>
     *     src != null
     *     rel != null
     * </pre>
     */
    E get(E src, R rel);

    // COMMANDES
    /**
     * Clear the network.
     * @post <pre>
     *     get(src, d) == null
     * </pre>
     */
    void clear();

    /**
     * Créer un lien entre src et dst au travers de la relation rel.
     * @param src l'élément d'origine
     * @param rel objet qui fera la relation entre src et dst
     * @param dst l'élément de destination
     * @pre <pre>
     *     src != null && d != null && dst != null
     * </pre>
     * @post <pre>
     *     Soit x ::= old get(src, d)
     *     x != null && x != dst ==> get(src, d) != x
     *     get(src, d) == dst
     * </pre>
     */
    void create(E src, R rel, E dst);
}
