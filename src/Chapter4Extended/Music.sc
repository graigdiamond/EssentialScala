/*

Mostly focusing on the structure of a guitar tab. which has sequences of individual notes, notes played
at the same time, and chords, with a timing that the sequence of playing is subdivided by

A note is simply a combination of the string and a fret, e.g., (1, 6) would be the first string,
6th fret.

A chord is a set of strings all played at the same time.  For example, an E is the collection of notes:
(1,0), (2,2), (3,2), (4,1), (5,0), (6,0), all played at the same time

There are many ways of playing the same chord at different forms.  An E can also be played as:
(1,4)(2,7)(3,6)(4,4)(5,5)(6,4).  So there would have to be some way to represent the various forms.

An even more advanced thing to do would be to have functions on chords that alter them in some way,
for example, the "sus4" function would change an E chord to an Esus4 chord, which looks like this:
(1,0), (2,2), (3,2), (4,2), (5,0), (6,0).  Unfortunately, I don't know enough about music theory
to know what this really means :-(

A chord can also have a muted note, so it needs to be represented by something richer than
an integer.  An E chord also looks like this: (1,X)(2,7)(3,9)(4,9)(5,9)(6,7).

The way a note transitions to another note also would need to be modeled.  So in a sequence of notes,
if a note on one string or set of strings is followed by a different note on the same string or set of strings,
then it can have a special kind of transition (e.g., a hammer, pulloff, or slide to), which is different than
just playing the note.

So, there would be a class "Song", which is comprised of sequences of frames, and each frame containing sequences of
sounds, where a sound can be a single note, a collection of notes to be played at the same time, or a chord (which is a special
collection of notes that is strummed rather than plucked).  There would also need to be a way to specify transitions where
the transitions are nonstandard.

 */


