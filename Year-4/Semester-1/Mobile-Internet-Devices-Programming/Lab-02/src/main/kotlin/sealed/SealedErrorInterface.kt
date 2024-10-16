package sealed

import java.io.File
import javax.sql.DataSource

sealed interface ErrorI

sealed class IOError() : ErrorI

class FileReadError(val file: File): IOError()
class DatabaseError(val source: DataSource): IOError()

object RuntimeError : ErrorI
