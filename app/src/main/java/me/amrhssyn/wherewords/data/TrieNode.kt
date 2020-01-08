package me.amrhssyn.wherewords.data

/**
 * data class of trie node
 */
class TrieNode {
    private var c: Char = ' '
    var children = HashMap<Char, TrieNode>()
    var isLeaf: Boolean = false

    constructor()
    constructor(c: Char) {
        this.c = c
    }
}
