class ResultFile
	attr_accessor :iterations, :sse, :percent_incorrect

	def initialize(filename)
		File.open(filename).each do |line|
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