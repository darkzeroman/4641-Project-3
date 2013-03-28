require 'descriptive_statistics'

class ClusterFile
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
			if line.include? "Incorrectly clustered instances :" then
					split_line = line.split(" ")
					@percent_incorrect = split_line[-2].to_f
			end

		end
	end
end


# puts Dir.pwd

ed_list = []
md_list = []

Dir.open(Dir.pwd).each do |file|

	if file.include? "ED" then
		ed_list << ClusterFile.new(file)
	elsif file.include? "MD"
		md_list << ClusterFile.new(file)
	elsif file.include? "PCA"
		md_list << ClusterFile.new(file)
	end
end

li = []
md_list.each { |cluster| li << cluster.percent_incorrect}
p li.join(',')
# p [li.mean, li.standard_deviation, li.variance].inspect.gsub("\[",'').gsub("\]",'')


