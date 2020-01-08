package me.amrhssyn.wherewords.data

/**
 * this class implemented trie data structure
 */
class Trie {

    private val root: TrieNode = TrieNode()

    /**
     * insert into trie
     */
    fun insert(word: String) {
        var children = root.children

        for (i in 0 until word.length) {
            val c = word[i]

            val t: TrieNode
            if (children.containsKey(c)) {
                t = children[c]!!
            } else {
                t = TrieNode(c)
                children[c] = t
            }

            children = t.children

            //set leaf node
            if (i == word.length - 1)
                t.isLeaf = true
        }
    }

    /**
     * search a word from trie
     */
    fun search(word: String): Boolean {
        val t = searchNode(word)

        return t != null && t.isLeaf
    }

    /**
     * search a node from trie
     */
    private fun searchNode(str: String): TrieNode? {
        var children: Map<Char, TrieNode> = root.children
        var t: TrieNode? = null
        for (i in 0 until str.length) {
            val c = str[i]
            if (children.containsKey(c)) {
                t = children[c]
                children = t!!.children
            } else {
                return null
            }
        }

        return t
    }
}