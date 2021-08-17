package com.itsradiix.commons.models;

public enum MusicNote {
	zero(0.5),
	one(calculatePitch(-11)),
	two(calculatePitch(-10)),
	three(calculatePitch(-9)),
	four(calculatePitch(-8)),
	five (calculatePitch(-7)),
	six(calculatePitch(-6)),
	seven(calculatePitch(-5)),
	eight(calculatePitch(-4)),
	nine(calculatePitch(-3)),
	ten(calculatePitch(-2)),
	eleven(calculatePitch(-1)),
	twelve(1.0),
	thirteen(calculatePitch(1)),
	fourteen(calculatePitch(2)),
	fifteen(calculatePitch(3)),
	sixteen(calculatePitch(4)),
	seventeen(calculatePitch(5)),
	eighteen(calculatePitch(6)),
	nineteen(calculatePitch(7)),
	twenty(calculatePitch(8)),
	twentyOne(calculatePitch(9)),
	twentyTwo(calculatePitch(10)),
	twentyThree(calculatePitch(11)),
	twentyFour(2.0);

	private final float pitch;

	MusicNote(double pitch){
		this.pitch = (float) pitch;
	}

	public float getPitch() {
		return pitch;
	}

	public static double calculatePitch(int integer){
		return Math.pow(2.0, integer / 12.0);
	}
}
