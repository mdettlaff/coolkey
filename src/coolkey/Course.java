package coolkey;

import java.io.Serializable;
import java.util.List;

/**
 * Kurs składający się z serii lekcji.
 */
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private List<Lesson> lessons;
	private int currentLessonIndex;

	public Course(String name, List<Lesson> lessons) {
		this.name = name;
		this.lessons = lessons;
		currentLessonIndex = 0;
	}

	public Lesson getCurrentLesson() {
		return lessons.get(currentLessonIndex);
	}

	/**
	 * Stopień ukończenia kursu.
	 *
	 * @return Numer aktualnej lekcji i ilość wszystkich lekcji w kursie.
	 */
	public int[] getProgress() {
		return new int[] { currentLessonIndex + 1, lessons.size() };
	}

	public void goToNextLesson() {
		currentLessonIndex++;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName() + ": " + lessons.toString();
	}
}
