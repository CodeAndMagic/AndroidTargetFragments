AndroidTargetFragments
======================

Android setTargetFragment() weirdness that I cannot comprehend...

Steps to reproduce:

+ Create a fragment (ParentFragment)
+ Create a fragment inside (ChildFragment) and call setTargetFragment() on the child to point to the ParentFragment.
+ (Optional) Use putFragment/getFragment methods to save the target fragment inside the ChildFragment.
+ Rotate device
+ Call getTargetFragment() on the child.

Surprise, surprise...it will not be the ParentFragment. Try the .apk below to see the results.

The workaround I'm using at the moment is to pass the ParentFragment tag (as String) to ChildFragment then when I need the parent, to look it up using getActivity().getFragmentManager().findFragmentByTag().

[Screenshot](Screenshot.png)