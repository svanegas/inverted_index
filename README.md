# inverted_index
This repository contains an implementation of an Inverted Index.

The basic idea to index the words is to create a Map that has each word
as key and a Set of phrases as value. So when we have to build the Indexes
we can map a word to a set of phrases. Also we can add or associate more
phrases to that word.
Then the queries would be easy, because we can look for the word in the map
and then retrieve all the phrases in the set returned.
