class ResultFile
	attr_accessor :iterations, :sse, :percent_incorrect, :confusion_matrix

	def initialize(filename)

		keep_going = false
		File.open(filename).each do |line|
			if line.include? "Confusion Matrix" then
				keep_going = true
			end

			if keep_going then
				if line.include? "=== Stratified" then
					keep_going = false
					next
				end

				@confusion_matrix = "" if not @confusion_matrix
				@confusion_matrix << line
			end

			if line.include? "Number of iterations:" then
					split_line = line.split(" ")
					@iterations = split_line.last.to_i
			end

			if line.include? "Within cluster sum of squared errors:" then
					split_line = line.split(" ")
					@sse = split_line.last.to_f
			end
			if line.include? "Incorrectly clustered instances" then
					split_line = line.split(" ")
					@percent_incorrect = split_line[-2].to_f
			end
			if line.include? "Incorrectly Classified Instances" then
					split_line = line.split(" ")
					@percent_incorrect = split_line[-2].to_f
			end

		end
	end
end