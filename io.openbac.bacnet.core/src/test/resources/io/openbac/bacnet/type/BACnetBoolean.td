# Application class with value false (lvt is value)
? class=false, value=false, tag=1, lvt=0
0x10
# Application class with value true (lvt is value)
? class=false, value=true, tag=1, lvt=1
0x11
# context class with value false (lvt is length now)
? class=true, value=false, tag=2, lvt=1
0x29 0x00
# context class with value true (lvt is length now)
? class=true, value=true, tag=2, lvt=1
0x29 0x01
# context with high tag number (> 14 to create extended tag)
? class=true, value=true, tag=171, lvt=1
0xf9 0xab 0x01
# ensure there is no final newline
