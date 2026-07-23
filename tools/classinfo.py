import sys, zipfile, struct

JAR = "build/moddev/artifacts/neoforge-21.1.234-merged.jar"

def desc_to_str(d):
    # convert a field/method descriptor to readable form
    i = 0
    arr = ""
    while i < len(d) and d[i] == '[':
        arr += "[]"; i += 1
    if i >= len(d): return d+arr
    c = d[i]
    base = {'B':'byte','C':'char','D':'double','F':'float','I':'int','J':'long',
            'S':'short','Z':'boolean','V':'void'}.get(c)
    if base:
        return base+arr
    if c == 'L':
        end = d.find(';', i)
        name = d[i+1:end].replace('/', '.')
        return name+arr
    return d[i:]+arr

def parse_method_desc(d):
    # (params)ret
    assert d[0]=='('
    i = 1; params=[]
    while d[i] != ')':
        j = i
        while d[j] == '[': j += 1
        if d[j] == 'L':
            end = d.find(';', j); params.append(desc_to_str(d[i:end+1])); i = end+1
        else:
            params.append(desc_to_str(d[i:j+1])); i = j+1
    ret = desc_to_str(d[d.find(')')+1:])
    return params, ret

def load_class(z, cls):
    try:
        data = z.read(cls)
    except KeyError:
        return None
    return data

def read_cp(data):
    pos = 8
    count = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
    utf8 = {}
    i = 1
    while i < count:
        tag = data[pos]; pos += 1
        if tag == 1:
            ln = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
            utf8[i] = data[pos:pos+ln].decode('utf-8', 'replace'); pos += ln
        elif tag == 3 or tag == 4:
            pos += 4
        elif tag == 5 or tag == 6:
            pos += 8; i += 1
        elif tag == 7 or tag == 8 or tag == 16:
            pos += 2
        elif tag == 9 or tag == 10 or tag == 11 or tag == 12 or tag == 17 or tag == 18:
            pos += 4
        elif tag == 15:
            pos += 3
        elif tag == 19 or tag == 20:
            pos += 2
        else:
            raise ValueError("unknown cp tag %d at %d" % (tag, pos))
        i += 1
    return utf8, pos

def methods(z, cls):
    data = load_class(z, cls)
    if data is None: return None
    utf8, pos = read_cp(data)
    # access_flags u2, this_class u2, super_class u2, interfaces_count u2
    pos += 2 + 2 + 2
    ic = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
    pos += ic * 2
    fc = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
    for _ in range(fc):
        # field: access u2, name u2, desc u2, attr_count u2, attrs
        pos += 2
        pos += 2 + 2
        ac = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
        for _ in range(ac):
            alen = struct.unpack('>I', data[pos+2:pos+6])[0]; pos += 6 + alen
    mc = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
    result = []
    for _ in range(mc):
        pos += 2  # access
        nidx = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
        didx = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
        name = utf8.get(nidx, '?'); desc = utf8.get(didx, '?')
        ac = struct.unpack('>H', data[pos:pos+2])[0]; pos += 2
        for _ in range(ac):
            alen = struct.unpack('>I', data[pos+2:pos+6])[0]; pos += 6 + alen
        result.append((name, desc))
    return result

def find_class(z, prefix):
    out = []
    for n in z.namelist():
        if n.endswith('.class') and n.startswith(prefix):
            out.append(n)
    return out

if __name__ == '__main__':
    z = zipfile.ZipFile(JAR)
    if len(sys.argv) < 3:
        print("usage: classinfo.py <classfile> <methodname-substr> [maxcount]")
        sys.exit(1)
    cls = sys.argv[1]
    sub = sys.argv[2]
    ms = methods(z, cls)
    if ms is None:
        # try to locate
        print("class not found directly; matches:")
        for n in find_class(z, cls.replace('.', '/'))[:20]:
            print("  ", n)
        sys.exit(0)
    shown = 0
    maxn = int(sys.argv[3]) if len(sys.argv) > 3 else 999
    for name, desc in ms:
        if sub.lower() in name.lower():
            try:
                params, ret = parse_method_desc(desc)
            except Exception:
                params, ret = ['?'], '?'
            print(f"  {name}({', '.join(params)}) -> {ret}")
            shown += 1
            if shown >= maxn: break
