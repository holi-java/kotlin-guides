import java.util.concurrent.CancellationException

fun cancel(): Nothing {
    throw CancellationException()
}