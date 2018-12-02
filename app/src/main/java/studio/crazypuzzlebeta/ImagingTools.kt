package studio.crazypuzzlebeta

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import java.nio.ByteBuffer


// Author: Mischa Jegerlehner
// This class contains some utility function that are needed for the puzzle creating
class ImagingTools {
    companion object {

        // Author: Mischa Jegerlehner
        // This is a utility function which returns a stringified byte array to store in the database
        public fun makeStringifiedByteArray(bitmap: Bitmap): String {
            val size = bitmap.getRowBytes() * bitmap.getHeight()
            val byteBuffer = ByteBuffer.allocate(size)
            bitmap.copyPixelsToBuffer(byteBuffer)

            return String(byteBuffer.array(), Charsets.UTF_8)
        }

        // Author: Mischa Jegerlehner
        // This function will take an imageView and split it up into puzzle pieces which
        // will the be assigned to the puzzle objects for later use in the generation process
        public fun splitToPuzzleObjects(length: Int, height: Int, source: ImageView): ArrayList<PuzzlePiece> {
            val rows: Int = length
            val columns: Int = height
            val piecesNumber: Int = rows * columns
            val piecesArray: ArrayList<PuzzlePiece> = ArrayList(piecesNumber)
            val tempImgView: ImageView = source
            val drawable: BitmapDrawable = tempImgView.drawable as BitmapDrawable
            val bitmap: Bitmap = drawable.bitmap
            val dimensions: IntArray = getBmpPosInImgV(source)
            val scaledBitmapLeft: Int = dimensions[0]
            val scaledBitmapTop: Int = dimensions[1]
            val scaledBitmapWidth: Int = dimensions[2]
            val scaledBitmapHeight: Int = dimensions[3]
            val croppedImageWidth: Int = scaledBitmapWidth - 2 * Math.abs(scaledBitmapLeft)
            val croppedImageHeight: Int = scaledBitmapHeight - 2 * Math.abs(scaledBitmapTop)
            val scaledBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap,scaledBitmapWidth,scaledBitmapHeight,true)
            val cropBmp: Bitmap = Bitmap.createBitmap(
                    scaledBitmap, Math.abs(scaledBitmapLeft), Math.abs(scaledBitmapTop), croppedImageWidth, croppedImageHeight
            )
            val pWidth: Int = croppedImageWidth / columns
            val pHeight: Int = croppedImageHeight / rows
            var ySteps = 0
            var xSteps: Int
            for (row: Int in 0 until rows) {
                xSteps = 0
                for (col: Int in 0 until columns) {
                    var offsetX = 0
                    var offsetY = 0
                    if (col > 0) {
                        offsetX = pWidth / 3
                    }
                    if (row > 0) {
                        offsetY = pHeight / 3
                    }
                    val resultBmp: Bitmap = Bitmap.createBitmap(
                            cropBmp, xSteps - offsetX, ySteps - offsetY, pWidth + offsetX, pHeight + offsetY
                    )
                    val puzzleObject = PuzzlePiece()
                    puzzleObject.bitmap = resultBmp
                    puzzleObject.originLeftMargin = xSteps.toFloat() - offsetX + source.left
                    puzzleObject.originTopMargin = ySteps.toFloat() - offsetY + source.top
                    puzzleObject.leftMargin = xSteps.toFloat() - offsetX + source.left
                    puzzleObject.topMargin = ySteps.toFloat() - offsetY + source.top
                    puzzleObject.width = pWidth + offsetX
                    puzzleObject.height = pHeight + offsetY
                    val pieceWidth: Int = puzzleObject.width
                    val pieceHeight: Int = puzzleObject.height
                    val pieceBitmap: Bitmap = puzzleObject.bitmap
                    puzzleObject.bitmap = generatePuzzleShape(
                            pieceWidth, pieceHeight, offsetX, offsetY, pieceBitmap, row, col, rows, columns
                    )
                    piecesArray.add(puzzleObject)
                    xSteps += pWidth
                }
                ySteps += pHeight
            }

            return piecesArray
        }

        // Author: Mischa Jegerlehner
        // In this function a very simple puzzle pattern is applied to the puzzle pieces
        private fun generatePuzzleShape(pieceWidth: Int, pieceHeight: Int, offsetX: Int, offsetY: Int, pieceBitmap: Bitmap, row: Int, col: Int, rows: Int, columns: Int): Bitmap {
            val puzzlePiece = Bitmap.createBitmap(pieceWidth + offsetX, pieceHeight + offsetY, Bitmap.Config.ARGB_8888)
            val bumpSize = pieceHeight / 4
            val canvas = Canvas(puzzlePiece)
            val path = Path()
            path.moveTo(offsetX.toFloat(), offsetY.toFloat())
            if (row == 0) {
                path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
            } else {
                path.lineTo((offsetX + (
                        pieceBitmap.width - offsetX) / 3).toFloat(), offsetY.toFloat()
                )
                path.cubicTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 6).toFloat(),
                        (offsetY - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 6 * 5).toFloat(),
                        (offsetY - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 3 * 2).toFloat(),
                        offsetY.toFloat()
                )
                path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
            }
            if (col == columns - 1) {
                path.lineTo(
                        pieceBitmap.width.toFloat(),
                        pieceBitmap.height.toFloat()
                )
            } else {
                path.lineTo(
                        pieceBitmap.width.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3).toFloat()
                )
                path.cubicTo(
                        (pieceBitmap.width - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6).toFloat(),
                        (pieceBitmap.width - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6 * 5).toFloat(),
                        pieceBitmap.width.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3 * 2).toFloat())
                path.lineTo(
                        pieceBitmap.width.toFloat(),
                        pieceBitmap.height.toFloat()
                )
            }
            if (row == rows - 1) {
                path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
            } else {
                path.lineTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 3 * 2).toFloat(),
                        pieceBitmap.height.toFloat()
                )
                path.cubicTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 6 * 5).toFloat(),
                        (pieceBitmap.height - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 6).toFloat(),
                        (pieceBitmap.height - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 3).toFloat(),
                        pieceBitmap.height.toFloat()
                )
                path.lineTo(
                        offsetX.toFloat(),
                        pieceBitmap.height.toFloat()
                )
            }
            if (col == 0) {
                path.close()
            } else {
                path.lineTo(
                        offsetX.toFloat(),
                        (offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2).toFloat()
                )
                path.cubicTo(
                        (offsetX - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5).toFloat(),
                        (offsetX - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.getHeight() - offsetY) / 6).toFloat(),
                        offsetX.toFloat(),
                        (offsetY + (pieceBitmap.getHeight() - offsetY) / 3).toFloat()
                )
                path.close()
            }
            val paint = Paint()
            paint.color = -0x1000000
            paint.style = Paint.Style.FILL
            canvas.drawPath(path, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(pieceBitmap, 0.0f, 0.0f, paint)
            var border = Paint()
            border.color = -0x7f000001
            border.style = Paint.Style.STROKE
            border.strokeWidth = 8.0f
            canvas.drawPath(path, border)
            border = Paint()
            border.color = -0x80000000
            border.style = Paint.Style.STROKE
            border.strokeWidth = 3.0f
            canvas.drawPath(path, border)

            return puzzlePiece
        }

        // Author: Mischa Jegerlehner
        // This function will return the bitmap position of a given imageView
        private fun getBmpPosInImgV(imageView: ImageView): IntArray {
            val bmpPosInImgV = IntArray(4)
            if(imageView.drawable == null) {
                return bmpPosInImgV
            }
            val f = FloatArray(9)
            imageView.imageMatrix.getValues(f)
            val scaleX: Float = f[Matrix.MSCALE_X]
            val scaleY: Float = f[Matrix.MSCALE_Y]
            val d: Drawable = imageView.drawable
            val origW: Int = d.intrinsicWidth
            val origH: Int = d.intrinsicHeight
            val actW: Int = Math.round(origW * scaleX)
            val actH: Int = Math.round(origH * scaleY)
            bmpPosInImgV[2] = actW
            bmpPosInImgV[3] = actH
            val imgViewW: Int = imageView.width
            val imgViewH: Int = imageView.height
            val top: Int = (imgViewH - actH) / 2
            val left: Int = (imgViewW - actW) / 2
            bmpPosInImgV[0] = left
            bmpPosInImgV[1] = top

            return bmpPosInImgV
        }
    }
}