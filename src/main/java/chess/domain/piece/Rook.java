package chess.domain.piece;

import chess.domain.Side;
import chess.domain.position.Position;

public class Rook extends Piece {
	private static final double SCORE = 5;

	public Rook(Side side, Position position) {
		super(side, position);
	}

	@Override
	public boolean isInPath(Position targetPosition) {
		return position.isSameCol(targetPosition) || position.isSameRow(targetPosition);
	}

	@Override
	public double getScore() {
		return SCORE;
	}
}
