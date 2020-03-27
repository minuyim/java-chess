package chess.domain;

import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessBoardTest {
	private ChessBoard chessBoard;

	@BeforeEach
	void setUp() {
		chessBoard = ChessBoardFactory.create();
	}

	@DisplayName("생성자를 호출한 경우 ChessBoard 인스턴스 생성")
	@ParameterizedTest
	@NullAndEmptySource
	void construct(List<Piece> pieces) {
		assertThatThrownBy(() -> new ChessBoard(pieces))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("지정한 포지션에 없는 말을 선택한 경우 에러 처리")
	void moveWithNonePiece() {
		assertThatThrownBy(() -> chessBoard.move(new Position("c3"), new Position("c4"), Side.WHITE))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("입력에 해당하는 말이 없습니다.");
	}

	@Test
	@DisplayName("자기 턴이 아닌 경우에 상대 말을 움직이면 에러 처리")
	void moveWithOtherSidePiece() {
		assertThatThrownBy(() -> chessBoard.move(new Position("a7"), new Position("a6"), Side.WHITE))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("본인의 말만 움직일 수 있습니다.");
	}

	@Test
	@DisplayName("규칙에 맞게 움직이지 않으면 에러처리")
	void moveWithOutRule() {
		assertThatThrownBy(() -> chessBoard.move(new Position("a2"), new Position("a5"), Side.WHITE))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("해당 위치로 기물을 옮길 수 없습니다.");
	}

	@Test
	@DisplayName("규칙에 맞게 공격하지 않으면 에러처리")
	void attackWithOutRule() {
		List<Piece> pieces = Arrays.asList(new King(Side.WHITE, new Position("d4")), new Pawn(Side.BLACK, new Position("a2")));
		assertThatThrownBy(() -> new ChessBoard(pieces).move(new Position("d4"), new Position("a2"), Side.WHITE))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("해당 위치로 기물을 옮길 수 없습니다.");
	}

	@Test
	@DisplayName("길이 막힌 경우 에러처리")
	void moveWithBlock() {
		assertThatThrownBy(() -> chessBoard.move(new Position("a1"), new Position("a3"), Side.WHITE))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("해당 위치로 가는 길이 막혀있습니다.");
	}

	@Test
	@DisplayName("점수 객체 생성 확인")
	void createStatus() {
		assertThat(chessBoard.createStatus()).isInstanceOf(ChessStatus.class);
	}

	@Test
	@DisplayName("판이 끝났는 지 확인")
	void isEnd() {
		List<Piece> pieces = Arrays.asList(new King(Side.WHITE, new Position("d4")), new Pawn(Side.BLACK, new Position("a2")));
		assertThat(new ChessBoard(pieces).isEnd()).isTrue();
	}
}