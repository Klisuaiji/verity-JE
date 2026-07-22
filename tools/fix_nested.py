#!/usr/bin/env python3
# Append the nested ScheduledTask / ScheduledSpawn classes the decompiler dropped.

edits = {
    "src/main/java/varmite/verity/event/ModEvents.java": '''

    private static class ScheduledTask {
        final Runnable task;
        int ticksRemaining;

        ScheduledTask(Runnable task, int ticksRemaining) {
            this.task = task;
            this.ticksRemaining = ticksRemaining;
        }
    }
''',
    "src/main/java/varmite/verity/event/VeritySpawnScheduler.java": '''

    private static class ScheduledSpawn {
        final ServerLevel level;
        final BlockPos pos;
        final long executeTick;

        ScheduledSpawn(ServerLevel level, BlockPos pos, long executeTick) {
            this.level = level;
            this.pos = pos;
            this.executeTick = executeTick;
        }
    }
''',
}

for path, nested in edits.items():
    with open(path, encoding='utf-8') as f:
        text = f.read()
    last = text.rfind('}')
    # insert nested class before the file's final closing brace
    new_text = text[:last] + nested + "\n}\n"
    with open(path, 'w', encoding='utf-8') as f:
        f.write(new_text)
    print(f"appended nested class to {path}")
